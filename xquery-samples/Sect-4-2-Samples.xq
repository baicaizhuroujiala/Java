(: XQuery main module :)

declare default element namespace "http://www.example.org/xquery/result";

import schema namespace n = "http://www.example.org/catalog"
    at "Fig-4-3.xsd";

declare namespace k = "http://www.example.org/titles"; 

import schema namespace r = "http://www.example.org/review"
    at "Fig-4-12.xsd";
    
declare function local:toAuteur ($info as element(n:info))
    as element(auteur)*  {
        for $n in $info/node() return
            typeswitch ($n)
            case $f as element(n:film)
                return 
                    for $d in $f/n:director
                    return element auteur { $d/text() }
            case $f as element(n:book)
                return element auteur { }
            case $f as element(n:radio)
                return element auteur { }
            default return { }
    };

(:
declare namespace n = "http://www.example.org/catalog";
:)

for $c in doc("Fig-4-11.xml")/n:catalog return $c;

for $t in doc("Fig-4-11.xml")/n:catalog/n:content/n:title
    return $t;

for $t in doc("Fig-4-11.xml")/n:catalog/n:content/n:title
    return <content> { $t } </content>;
    
<catalog> {
    for $t in doc("Fig-4-11.xml")/n:catalog/n:content/n:title
    return <content> { $t } </content>
} </catalog>;

<k:titles> {
    for $c in doc("Fig-4-11.xml")/n:catalog/n:content
    return
        <k:title k:category="{$c/@category}"> 
            { $c/n:title/text() }
        </k:title>
} </k:titles>;


element { fn:upper-case("titles") }
     {
        for $c in doc("Fig-4-11.xml")/n:catalog/n:content
        return element { fn:upper-case("title") }
                       {
                         attribute category { $c/@category },
                         $c/n:title/text()
                       }
    };
    
element k:titles
    {
        for $c in doc("Fig-4-11.xml")/n:catalog/n:content
        return element k:title
                       {
                         attribute category { $c/@category },
                         $c/n:title/text()
                       }
    };
    
<imdb> {
    for $c in doc("Fig-4-11.xml")/n:catalog/n:content,
        $r in doc("Fig-4-13.xml")/r:reviews/r:review
    where $c/n:title = $r/r:title and $c/n:pubDate = $r/r:pubDate
    return
        <info>
           <title> { $c/n:title/text() } </title>
           <pubDate> { $c/n:pubDate/text() } </pubDate>
           <director> { $c/n:info/n:film/n:director/text()} </director>
           <reviewer> { $r/r:reviewer/text() } </reviewer>
           <eval lang="{$r/@lang}"> { $r/r:eval/text() } </eval>
        </info>
} </imdb>;

<imdb> {
    for $c in doc("Fig-4-11.xml")/n:catalog/n:content,
        $r in doc("Fig-4-13.xml")/r:reviews/r:review
    where $c/n:title = $r/r:title and $c/n:pubDate = $r/r:pubDate
    return
        <info>
           <title> { $c/n:title/text() } </title>
           <pubDate> { $c/n:pubDate/text() } </pubDate>
           { local:toAuteur($c/n:info) }
           <reviewer> { $r/r:reviewer/text() } </reviewer>
           <eval lang="{$r/@lang}"> { $r/r:eval/text() } </eval>
        </info>
} </imdb>;

<imdb> {
    for $c in doc("Fig-4-11.xml")/n:catalog/n:content
    return
    <info>
        <title> { $c/n:title/text() } </title> 
        <pubDate> { $c/n:pubDate/text() } </pubDate>
        { local:toAuteur($c/n:info) }
        <reviews>
        {   for $r in doc("Fig-4-13.xml")/r:reviews/r:review 
            where $c/n:title = $r/r:title and $c/n:pubDate = $r/r:pubDate return
            <review>
                <reviewer> { $r/r:reviewer/text() } </reviewer>
                <eval lang="{$r/@lang}"> { $r/r:eval/text() } </eval>
            </review>
        }
        </reviews>
    </info>
} </imdb>;

<reviewers> {
    for $r in
        distinct-values(doc("Fig-4-13.xml")/r:reviews/r:review/r:reviewer)
    return
        <info>
            <reviewer> { string($r) } </reviewer>
            <reviews>
            {   for $rr in doc("Fig-4-13.xml")/r:reviews/r:review
                where $r = $rr/r:reviewer 
                return
                <review>
                    <title> { $rr/r:title/text() } </title>
                    <pubDate> { $rr/r:pubDate/text() } </pubDate>
                    <eval> { $rr/r:eval/text() } </eval>
                </review>
            }
            </reviews>
        </info>
} </reviewers>
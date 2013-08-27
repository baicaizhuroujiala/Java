(: import library module and point the processor to the file containing the library module :)
import module namespace libr = 'http://www.example.com/Library' at 'Library.xq';

(: now, our main module looks very clean having no variable and function declarations, 
   but only a query body :)
<test>
    <message1>{ 
        let $idx := 1 
        return lib:message($idx) 
    }</message1>
    <message2>{ 
        let $idx := 2 
        return lib:message($idx) 
    }</message2>
</test>
                



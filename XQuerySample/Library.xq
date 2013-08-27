module namespace libr = 'http://www.example.com/Library';

(: declaring the library module namespace;
   the namespace and the file name containing the module can be chosen freely.
   Yet, it is a good practice to use the file name as the last path token in the namespace.
   -> here: hello_library_module is the ending of the namespace and also the base filename "hello_library_module.xq" :)

(: reusable variable and function declarations;
   we had to add the "lib" prefix to bind the variable and function
   declarations to the library module namespace :)
declare variable $lib:hello := ("Hello World1","Hello World2");

declare function lib:message ($number as xs:integer) as item() {
    $lib:hello[$number]
};
                
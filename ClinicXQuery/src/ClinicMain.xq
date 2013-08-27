(: XQuery main module :)

import schema namespace clin = "http://www.example.org/schemas/clinic" at "Clinic.xsd";
import schema namespace pat = "http://www.example.org/schemas/clinic/patient" at "Patient.xsd";
declare namespace ps = "http://www.example.org/schmeas/clinic/patients";
import module namespace f = "http://www.example.com/xquery/clinic/FindExample" at "FindExample.xq";
import schema namespace trmt = "http://www.example.org/schemas/clinic/treatment" at "Treatment.xsd";
import schema namespace prov = "http://www.example.org/schemas/clinic/provider" at "Provider.xsd"; 

let $clinic := doc("ClinicData.xml")/clin:Clinic
return f:getTreatmentInfo($clinic)

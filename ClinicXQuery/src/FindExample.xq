(: XQuery main module :)

module namespace find = "http://www.example.com/xquery/clinic/FindExample";

import schema namespace clin = "http://www.example.org/schemas/clinic" at "Clinic.xsd";
import schema namespace pat = "http://www.example.org/schemas/clinic/patient" at "Patient.xsd";
import schema namespace trmt = "http://www.example.org/schemas/clinic/treatment" at "Treatment.xsd";
import schema namespace prov = "http://www.example.org/schemas/clinic/provider" at "Provider.xsd";


declare function find:getPatientTreatments ($nowClinic as element(clin:Clinic), $nowPatientId as xs:string)
         as element(find:PatientTreatments)*    {
        <find:PatientTreatments> { $nowClinic/pat:Patient[pat:patient-id = $nowPatientId]//pat:treatment } </find:PatientTreatments>         
};

declare function find:getPatientDrugs($nowClinic as element(clin:Clinic), $nowPatientId as xs:string)
         as element(find:PatientDrugs)*    {
        <find:PatientDrugs> { 
        let $nowPatient := $nowClinic/pat:Patient[pat:patient-id = $nowPatientId]
        for $tempTreatment in $nowPatient//pat:treatment
        return for $tempDrug-Treatment in $tempTreatment/trmt:drug-treatment
               return 
               <find:PatientDrugsInfo>
               <find:PatientDrugsInfo-Name> { $tempDrug-Treatment/trmt:name/text() } </find:PatientDrugsInfo-Name>
               <find:PatientDrugsInfo-Dosage> { $tempDrug-Treatment/trmt:dosage/text() } </find:PatientDrugsInfo-Dosage>
               <find:PatientDrugsInfo-Dianosis> { $tempTreatment/trmt:diagnosis/text() } </find:PatientDrugsInfo-Dianosis>
               </find:PatientDrugsInfo>
        } </find:PatientDrugs>         
};



declare function find:getTreatmentInfo($nowClinic as element(clin:Clinic)) 
        as element(find:Treatments){
        <find:Treatments> {
        for $tempTreatmentWay in distinct-values(find:displayTreatmentWithTypeDivide($nowClinic//pat:treatment)//find:displayTreatment-type)
        return <find:TreatmentsInfo>
               <find:TreatmentsInfo-TreatmentsType> { $tempTreatmentWay } </find:TreatmentsInfo-TreatmentsType>
                    <find:TreatmentsInfo-More> { 
                    for $tempPatient in $nowClinic/pat:Patient
                    return for $tempTreatment in $tempPatient//pat:treatment
                           where find:displayTreatmentWithTypeDivide($tempTreatment)//find:displayTreatment-type = $tempTreatmentWay
                           return
                           <find:TreatmentsInfo-Id> 
                           <find:TreatmentsInfo-PatientId> { $tempPatient/pat:patient-id/text() } </find:TreatmentsInfo-PatientId>
                           <find:TreatmentsInfo-ProviderId> { $tempTreatment/trmt:provider-id/text() } </find:TreatmentsInfo-ProviderId>
                           </find:TreatmentsInfo-Id>
                    }</find:TreatmentsInfo-More>
        </find:TreatmentsInfo>
        } </find:Treatments>
};


declare function find:getProviderInfo($nowClinic as element(clin:Clinic)) 
        as element(find:Providers){
        <find:Providers> { 
        for $tempProviderId in distinct-values($nowClinic/prov:Provider/prov:provider-id)
            return <find:ProvidersInfo> 
                   <find:ProvidersInfo-ProviderId> { $tempProviderId } </find:ProvidersInfo-ProviderId>      
                        <find:ProvidersInfo-More> {
                        for $tempPatient in $nowClinic/pat:Patient
                        where $tempProviderId = $tempPatient//pat:treatment/trmt:provider-id
                        return 
                        <find:ProvidersInfo-Patient>
                        <find:ProvidersInfo-PatientId> { $tempPatient/pat:patient-id/text() } </find:ProvidersInfo-PatientId>
                        <find:ProvidersInfo-Treatments> { find:displayTreatment($tempPatient//pat:treatment[trmt:provider-id=$tempProviderId]) } </find:ProvidersInfo-Treatments>
                        </find:ProvidersInfo-Patient>
                        }</find:ProvidersInfo-More>
                  </find:ProvidersInfo> 
        } </find:Providers>
};


declare function find:getDrugInfo($nowClinic as element(clin:Clinic)) 
        as element(find:Drugs){
        <find:Drugs> {  
        for $tempDurg in distinct-values($nowClinic//trmt:drug-treatment/trmt:name) (:distinct-values function to move dupelication:)
        return  
        <find:DrugsInfo>
                <find:DrugsInfo-Name> { $tempDurg } </find:DrugsInfo-Name> 
                <find:DrugsInfo-More> { 
                for $tempPatient in $nowClinic/pat:Patient
                where $tempDurg = $tempPatient//trmt:drug-treatment/trmt:name
                return for $tempTreatment in $tempPatient//pat:treatment    (:To get provider-Id and diagnosis have to get corresponding treatment, which is drug treatment:)
                       return for $tempDrugTreatment in $tempTreatment/trmt:drug-treatment
                              return 
                              <find:DrugsInfo-Patient>
                              <find:DrugsInfo-PatientId> { $tempPatient/pat:patient-id/text() } </find:DrugsInfo-PatientId>
                              <find:getDrugInfo-ProviderId> { $tempTreatment/trmt:provider-id/text() } </find:getDrugInfo-ProviderId>
                              <find:DrugsInfo-Diagnosis> { $tempTreatment/trmt:diagnosis/text()} </find:DrugsInfo-Diagnosis>
                              </find:DrugsInfo-Patient>
                }
                </find:DrugsInfo-More>
        </find:DrugsInfo> 
        }</find:Drugs>
};

declare function find:displayTreatment($nowTreatment as element(pat:treatment)*)
        as element(find:displayTreatment)*{
        <find:displayTreatment> {
        for $tempNodeTreatment in $nowTreatment/node()
        return typeswitch ($tempNodeTreatment)
        case $d as element(trmt:drug-treatment)
        return  "drug-treatment" 
        case $r as element(trmt:radiology)
        return "radiology"
        case $s as element(trmt:surgery)
        return "surgery" 
        default return{}
        }</find:displayTreatment>
};

declare function find:displayTreatmentWithTypeDivide($nowTreatment as element(pat:treatment)*)
        as element(find:displayTreatment)*{
        <find:displayTreatment> {
        for $tempNodeTreatment in $nowTreatment/node()
        return typeswitch ($tempNodeTreatment)
        case $d as element(trmt:drug-treatment)
        return  <find:displayTreatment-type> {"drug-treatment"} </find:displayTreatment-type>
        case $r as element(trmt:radiology)
        return  <find:displayTreatment-type> {"radiology"}</find:displayTreatment-type>
        case $s as element(trmt:surgery)
        return <find:displayTreatment-type> { "surgery" } </find:displayTreatment-type> 
        default return{}
        }</find:displayTreatment>
};



package sbc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.RDFWriter;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

/**
 * @Title CSVGeneratorRDF
 * @Description CSVGen to create resource from a CSV file with peope information
 * @author Jean Paul Mosquera Arevalo
 */
public class CSVGeneratorRDF1 {
    //CAMBIAR POR RUTAS DEL EQUIPO DONDE SE EJECUTE
    static String DataFilePath= "C:/DataPerson.csv"; //Data
    static String GenFilePath= "/Users/Estevan/Documents/NetbeansProjects/Proyecto_SBC/prueba.rdf"; //Generated RDF
    public static void main(String... args) throws FileNotFoundException {
        //Get data from CSV and store in a list
        List<Person> persons = readPersonsFromCSV(DataFilePath);
        
        // create an empty Model
        Model model = ModelFactory.createDefaultModel();
        File f= new File (GenFilePath); //File to save the results of RDF Generation
        FileOutputStream os = new FileOutputStream(f);
        
        //Set prefix for the URI base (data)
        String dataPrefix = "http://utpl.edu.ec/sbc/data/";
        model.setNsPrefix("data",dataPrefix);
        
        //Vocab and models present in JENA
        //SCHEMA
        String schema = "http://schema.org/";
        model.setNsPrefix("schema", schema);
        Model schemaModel = ModelFactory.createDefaultModel();
        //Dbpedia Ontology- DBO
        String dbo = "http://dbpedia.org/ontology/";
        model.setNsPrefix("dbo", dbo);
        Model dboModel = ModelFactory.createDefaultModel(); 
        //Dbpedia Resource - DBR
        String dbr = "http://dbpedia.org/resource/";
        model.setNsPrefix("dbr", dbr);
        Model dbrModel = ModelFactory.createDefaultModel(); 
        //Geonames - gn
        String gn = "http://www.geonames.org/ontology#";
        model.setNsPrefix("gn", gn);
        Model gnModel = ModelFactory.createDefaultModel(); 
        //Remove the data header from the list
        persons.remove(0);
        // let's print all the person read from CSV file
        for (Person b : persons) {
            //System.out.println(b);
            String givenName="", familyName="";
            String email="";
            String padre="";
            String madre="";
            /**
             * Formatting and resource creation by the Occupation
             */
            String ocuppation=b.getOccupation().replaceAll(" ", "_");
            Resource rOc=model.createResource(dataPrefix+ocuppation)
                    .addProperty(RDF.type, dboModel.getProperty(dbo, "PersonFunction"))
                    .addProperty(RDFS.label, b.getOccupation());
            /**
             * Resource Country
             */
            Resource rC=model.createResource(dbr+b.getCountry())
                    .addProperty(RDF.type, dboModel.getProperty(dbo, "Country"))
                    .addProperty(gnModel.getProperty(gn,"countryCode"), b.getCc());
            String personURI    = dataPrefix+b.getId(); //URI creation with DNI
            String[] partsName = b.getName().split(" "); //Splitting String name
            //System.out.println(partsName.length);
            /**
             * Portion code to validate if the person has first name, second name
             * father lastname, mother lastname or only have the first name and 
             * lastname
             */
            if(partsName.length==4){
                givenName    = partsName[0]+" "+partsName[1];
                familyName   = partsName[2]+" "+partsName[3]; 
            }else{
                givenName    = partsName[0];
                familyName   = partsName[2]; 
            }
            if(!b.getEmail().equals("")){
                email=b.getEmail();
            }

            String fullName     = givenName + " " + familyName;
            //System.out.println(givenName);
            //System.out.println(familyName);
            //System.out.println(personURI);
            Resource person;
            /**
             * Portion code to generate the Resource Person before the existence
             * of email is verified, if the email doesnt exist this property isnt
             * added in the Resource (schema:email) and if the email exist this
             * property is added in the Resource
             */
            if(email!=""){
                  person = model.createResource(personURI)
                 .addProperty(RDF.type, FOAF.Person)
                 .addProperty(FOAF.firstName, givenName)
                 .addProperty(FOAF.lastName, familyName )
                 .addProperty(schemaModel.getProperty(schema,"email"), b.getEmail() )
                 .addProperty(dboModel.getProperty(dbo,"country"), dbrModel.getProperty(dbr,b.getCountry()));
                 
            }else{
                 person = model.createResource(personURI)
                 .addProperty(RDF.type, FOAF.Person)
                 .addProperty(FOAF.firstName, givenName)
                 .addProperty(dboModel.getProperty(dbo,"country"), rC);
            }
            /**
             * Portion code to generate the relation father or mother between two resources of type Person
             */
            if(!b.getFather().equals("")){
                String padreUri=dataPrefix+b.getFather();
                person.addProperty(dboModel.getProperty(dbo,"father"), model.getProperty(dataPrefix,b.getFather()));
            }
            if(!b.getMother().equals("")){
                person.addProperty(dboModel.getProperty(dbo,"mother"), model.getProperty(dataPrefix,b.getMother()));
            }
            person.addProperty(dboModel.getProperty(dbo,"occupation"), rOc);
            
            
        }
        /**
         * Reading the Generated data in Triples Format and RDF
         */
        StmtIterator iter = model.listStatements();
        System.out.println("TRIPLES");
        while (iter.hasNext()) {
            Statement stmt      = iter.nextStatement();  // get next statement
            Resource  subject   = stmt.getSubject();     // get the subject
            Property  predicate = stmt.getPredicate();   // get the predicate
            RDFNode   object    = stmt.getObject();      // get the object

            System.out.print(subject.toString());
            System.out.print(" " + predicate.toString() + " ");
            if (object instanceof Resource) {
               System.out.print(object.toString());
            } else {
                // object is a literal
                System.out.print(" \"" + object.toString() + "\"");
            }

            System.out.println(" .");
         }
                // now write the model in XML form to a file
        System.out.println("MODELO RDF------");
        model.write(System.out, "RDF/XML-ABBREV");
        
        // Save to a file
        RDFWriter writer = model.getWriter("RDF/XML");
        writer.write(model,os, "");
        
        //Close models
        dboModel.close();
        model.close();
    }
    
   
    /**
     * Method to read and instance objects from CSV 
     * @param fileName Route of file
     * @return List of Persons from the CSV metadata
     */
    private static List<Person> readPersonsFromCSV(String fileName) {
        List<Person> persons = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);

        // create an instance of BufferedReader
        // using try with resource, Java 7 feature to close resources
        try (BufferedReader br = Files.newBufferedReader(pathToFile)) {

            // read the first line from the text file
            String line = br.readLine();

            // loop until all lines are read
            while (line != null) {

                // use string.split to load a string array with the values from
                // each line of
                // the file, using a comma as the delimiter
                String[] attributes = line.split(",");

                Person person = createPerson(attributes);

                // adding person into ArrayList
                persons.add(person);

                // read next line before looping
                // if end of file reached, line would be null
                line = br.readLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return persons;
    }
    /**
     * Method createPerson
     * @param metadata attributes obtained from the read line of CSV
     * @return an Objet of type person
     */
    private static Person createPerson(String[] metadata) {
        String id = metadata[0];
        String name = metadata[1];
        String country = metadata[2];     
        String cc=metadata[3];
        String email = metadata[4];
        String father = metadata[5];        
        String mother = metadata[6];
        String occupation = metadata[7];        
        // create and return person of this metadata
        return new Person(id,name,country,cc,email,father,mother,occupation);
    }

}

//Person Class
class Person {
    private String id;
    private String name;
    private String country;
    private String cc;
    private String email;
    private String father;
    private String mother;
    private String occupation;
    
    
    public Person(String id, String name, String country,String cc, String email,String father, String mother, String occupation ) {
        this.id=id;
        this.name = name;
        this.country=country;
        this.email=email;
        this.cc=cc;
        this.father=father;
        this.mother=mother;
        this.occupation=occupation;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", name=" + name + ", country=" + country + ", email=" + email + ", father=" + father + ", mother=" + mother + ", occupation=" + occupation + '}';
    }



}




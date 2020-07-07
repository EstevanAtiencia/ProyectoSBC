package sbc;

import Clases.casos;
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
class CSVGeneratorRDF {

    //CAMBIAR POR RUTAS DEL EQUIPO DONDE SE EJECUTE
    static String DataFilePath = "/Users/Estevan/Documents/NetbeansProjects/Proyecto_SBC/Europa-Date.csv"; //Data
    static String GenFilePath = "/Users/Estevan/Documents/NetbeansProjects/Proyecto_SBC/casos.rdf"; //Generated RDF

    public static void main(String... args) throws FileNotFoundException {
        //Get data from CSV and store in a list

        List<casos> cases = readPersonsFromCSV(DataFilePath);

        // create an empty Model
        Model model = ModelFactory.createDefaultModel();
        File f = new File(GenFilePath); //File to save the results of RDF Generation
        FileOutputStream os = new FileOutputStream(f);

        //Set prefix for the URI base (data)
        String dataPrefix = "http://utpl.edu.ec/sbc/data/";
        model.setNsPrefix("data", dataPrefix);

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
        cases.remove(0);
        // let's print all the person read from CSV file
        for (casos b : cases) {

            /**
             * Resource continent
             */
            /**
             * Resource Country
             */
            Resource rC = model.createResource(dbr + b.getCountry())
                    .addProperty(RDF.type, dboModel.getProperty(dbo, "Country"));
                    
        }
        /**
         * Reading the Generated data in Triples Format and RDF
         */
        StmtIterator iter = model.listStatements();
        System.out.println("TRIPLES");
        while (iter.hasNext()) {
            Statement stmt = iter.nextStatement();  // get next statement
            Resource subject = stmt.getSubject();     // get the subject
            Property predicate = stmt.getPredicate();   // get the predicate
            RDFNode object = stmt.getObject();      // get the object

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
        writer.write(model, os, "");

        //Close models
        dboModel.close();
        model.close();
    }

    /**
     * Method to read and instance objects from CSV
     *
     * @param fileName Route of file
     * @return List of Persons from the CSV metadata
     */
    private static List<casos> readPersonsFromCSV(String fileName) {
        List<casos> casess = new ArrayList<>();
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

                casos cases = create(attributes);

                // adding person into ArrayList
                casess.add(cases);

                // read next line before looping
                // if end of file reached, line would be null
                line = br.readLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return casess;
    }

    /**
     * Method createPerson
     *
     * @param metadata attributes obtained from the read line of CSV
     * @return an Objet of type person
     */
    private static casos create(String[] metadata) {

        String continent = metadata[0];
        String iso_code = metadata[1];
        String country = metadata[2];
        String longitude = metadata[3];
        String latitude = metadata[4];
        String date = metadata[5];
        String total_case = metadata[6];
        String new_cases = metadata[7];
        String total_deaths = metadata[8];
        String new_deaths = metadata[9];
        String total_tests = metadata[10];
        String new_tests = metadata[11];
        String positives = metadata[12];
        String negatives = metadata[13];
        String realized = metadata[14];
        String not_confirmed = metadata[15];
        String active_caso = metadata[16];
        String recovered = metadata[17];
        String confirmed = metadata[18];
        String hospitalized = metadata[19];
        String population = metadata[20];
        String gdp_per_capita = metadata[21];

        // create and return casos
        return new casos(0);
    }

}

//casos Class


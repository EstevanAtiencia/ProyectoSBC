package sbc;

import Clases.casos;
import Clases.casosstring;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
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
class GeneratorRDF {

    static String DataFilePath = "/Users/Estevan/Documents/NetbeansProjects/Proyecto_SBC/Europa-Date6.csv"; //Data
    static String GenFilePath = "/Users/Estevan/Documents/NetbeansProjects/Proyecto_SBC/casos.rdf"; //Generated RDF

    public static void main(String... args) throws FileNotFoundException {
        //Get data from CSV and store in a list

        ArrayList<casosstring> caso = new ArrayList();
        /*Leer csv*/
        BufferedReader bufferLectura = null;
        try {
            bufferLectura = new BufferedReader(new FileReader(DataFilePath));
            String titulo = bufferLectura.readLine();
            String linea = bufferLectura.readLine();
            String[] campos;

            while (linea != null) {
                campos = linea.split(",");
                //System.out.println(Arrays.toString(campos));
                linea = bufferLectura.readLine();

                casosstring cases = new casosstring(campos[0], campos[1], campos[2], campos[3], campos[4], campos[5],
                        campos[6], campos[7],
                        campos[8], campos[9], campos[10], campos[11], campos[12], campos[13], campos[14], campos[15],
                        campos[16], campos[17], campos[18], campos[19], campos[20]);
                caso.add(cases);

            }
        } catch (IOException e) {
        } finally {
            // Cierro el buffer de lectura
            if (bufferLectura != null) {
                try {
                    bufferLectura.close();
                } catch (IOException e) {
                }
            }
        }

        // create an empty Model
        Model model = ModelFactory.createDefaultModel();
        File f = new File(GenFilePath); //File to save the results of RDF Generation
        FileOutputStream os = new FileOutputStream(f);

        //Set prefix for the URI base (data)
        String dataPrefix = "http://utpl.edu.ec/sbc/dataCOVID/";
        model.setNsPrefix("data", dataPrefix);

        String newOnto = "http://utpl.edu.ec/sbc/data/Ontology";
        model.setNsPrefix("newOnto", newOnto);

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

        // let's print all the person read from CSV file
        for (casosstring b : caso) {

            Resource con = model.createResource(dbo + b.getContinent())
                    .addProperty(RDF.type, dboModel.getProperty(dbo, "Continent"))
                    .addProperty(RDFS.label, b.getContinent());
            
            Resource rC = model.createResource(dbr + b.getCountry())
                    .addProperty(dboModel.getProperty(dbo, "Continent"), dboModel.getProperty(dbo, b.getContinent()))
                    .addProperty(RDF.type, dboModel.getProperty(dbo, "country"))
                   
                    .addProperty(gnModel.getProperty(gn, "countryCode"), b.getIso_code())
                    .addProperty(schemaModel.getProperty(schema, "latitude"), b.getLatitude())
                    .addProperty(schemaModel.getProperty(schema, "longitude"), b.getLongitude())
                    .addProperty(schemaModel.getProperty(schema, "populationTotal"), b.getPopulation());
                   // .addProperty(dboModel.getProperty(dbo, "grossDomesticProductNominalPerCapita"), b.getGdp_per_capita());

            
           /* Resource ob = model.createResource(schema + "Observation")
                    .addProperty(RDF.type, dboModel.getProperty(newOnto, "Staticts"));

            Resource st = model.createResource(newOnto + "Staticts")
                    .addProperty(RDF.type, dboModel.getProperty(newOnto, "Active_cases"))
                    .addProperty(RDF.type, dboModel.getProperty(newOnto, "Confirmed_cases"))
                    .addProperty(RDF.type, dboModel.getProperty(newOnto, "Deaths_cases"))
                    .addProperty(RDF.type, dboModel.getProperty(newOnto, "Hospitalized_cases"))
                    .addProperty(RDF.type, dboModel.getProperty(newOnto, "Recovered_cases"));*/

            /*Resource ca = model.createResource(newOnto + "Active_cases")
                    .addProperty(dboModel.getProperty(dbo,"Country"),dboModel.getProperty(dbo,b.getCountry()))
                    .addProperty(RDFS.label, "Active Cases")
                    .addProperty(dboModel.getProperty(dbo,"date"),b.getDate() );
                    

            Resource cc = model.createResource(newOnto + "Confirmed_cases")
                    .addProperty(dboModel.getProperty(dbo,"Country"),dboModel.getProperty(dbo,b.getCountry()))
                    .addProperty(RDFS.label, "Confirmed_cases")
                    .addProperty(dboModel.getProperty(dbo,"date"),b.getDate() );

            Resource dc = model.createResource(newOnto + "Deaths_cases")
                    .addProperty(dboModel.getProperty(dbo,"Country"),dboModel.getProperty(dbo,b.getCountry()))
                    .addProperty(RDFS.label, "Deaths_cases")
                    .addProperty(dboModel.getProperty(dbo,"date"),b.getDate() );

            Resource hc = model.createResource(newOnto + "Hospitalized_cases")
                    .addProperty(dboModel.getProperty(dbo,"Country"),dboModel.getProperty(dbo,b.getCountry()))
                    .addProperty(RDFS.label, "Hospitalized_cases")
                    .addProperty(dboModel.getProperty(dbo,"date"),b.getDate() );

            Resource rc = model.createResource(newOnto + "Recovered_cases")
                    .addProperty(dboModel.getProperty(dbo,"Country"),dboModel.getProperty(dbo,b.getCountry()))
                    .addProperty(RDFS.label, "Recovered_cases")
                    .addProperty(dboModel.getProperty(dbo,"date"),b.getDate() );*/
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
        model.write(System.out);
        model.write(System.out, "N3");
        // now write the model in XML form to a file
     

        // Save to a file
        RDFWriter writer = model.getWriter("RDF/XML");
        writer.write(model, os, "");

        //Close models
        dboModel.close();
        model.close();
    }

}

//casos Class


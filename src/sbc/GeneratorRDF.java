package sbc;


import Clases.ClasePrincpial;
import Clases.Continente;
import Clases.Dataset;
import Clases.Pais;
import Clases.Total_Activos;
import Clases.Total_Recuperados;
import Clases.Total_casos;
import Clases.Total_hospitalizados;
import Clases.Total_muertes;
import Clases.Total_pruebas;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    static String DataFilePath = "/Users/Estevan/Documents/NetbeansProjects/Proyecto_SBC/Europa-Date8.csv"; //Data
    static String GenFilePath = "/Users/Estevan/Documents/NetbeansProjects/Proyecto_SBC/DatosGenerados.rdf"; //Generated RDF

    public static void main(String... args) throws FileNotFoundException, ParseException {
        //Get data from CSV and store in a list
       
        List<Total_casos> LTC = new ArrayList<>();
          List<Total_Activos> LAC = new ArrayList<>();
        /*Leer csv*/
        BufferedReader bufferLectura = null;
        try {
            bufferLectura = new BufferedReader(new FileReader(DataFilePath));
            String titulo = bufferLectura.readLine();
            String linea = bufferLectura.readLine();
            String[] campos;
            Date fecha= new Date();
            int i=1;
            while (linea != null) {
                campos = linea.split(";");
                linea = bufferLectura.readLine();
                Continente cont= new Continente(campos[0]);
                Pais pais = new Pais(campos[1],campos[2],campos[3],campos[4],campos[20],campos[21],cont);
                System.out.println(String.valueOf(campos[3]));
                Dataset ds= new Dataset(campos[22],campos[23],campos[24],campos[25],campos[26],campos[27]);
                Total_Activos TA= new Total_Activos("Act"+i,campos[16]);
                Total_muertes tm= new Total_muertes("Muer"+i,campos[8],campos[9]);
                Total_hospitalizados th= new Total_hospitalizados("Hosp"+i,campos[19]);
                Total_Recuperados tr = new Total_Recuperados("Recu"+i,campos[17]);
                Total_pruebas tp=new Total_pruebas("Prue"+i,campos[10], campos[11]);
                System.out.println(campos[5]);
                Total_casos TC= new Total_casos("Confr"+i,campos[5], campos[6],campos[7], pais,ds,TA,tm,th,tr,tp);
                LAC.add(TA);
                LTC.add(TC);
                i++;
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
        
        String newOnto = "http://utpl.edu.ec/sbc/data/Ontology/";
        model.setNsPrefix("newOnto", newOnto);
        Model newOntoM = ModelFactory.createDefaultModel();
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
        
        String dcat = "http://www.w3.org/ns/dcat#";
        model.setNsPrefix("dcat", dcat);
        Model dcatModel = ModelFactory.createDefaultModel();
        //Remove the data header from the list
        
        
        String dc = "http://purl.org/dc/elements/1.1/";
        model.setNsPrefix("dc", dc);
        Model dcModel = ModelFactory.createDefaultModel();
        
        
        String prov = "http://www.w3.org/ns/prov#";
        model.setNsPrefix("prov", prov);
        Model provModel = ModelFactory.createDefaultModel();
   

       
        for (Total_casos total_casos : LTC) {
            
           Resource con = model.createResource(dataPrefix + total_casos.getPais().getCon().getNombre())
                    .addProperty(RDF.type, dboModel.getProperty(dbo, "Continent"))
                    .addProperty(dboModel.getProperty(dbo,"name"),total_casos.getPais().getCon().getNombre() );
           
           String aux=total_casos.getPais().getNombre().replaceAll(" ", "_");
           
            Resource rC = model.createResource(dataPrefix + aux)
                    .addProperty(RDF.type, dboModel.getProperty(dbo, "Country"))
                    .addProperty(dboModel.getProperty(dbo,"name"),total_casos.getPais().getNombre() )
                    .addProperty(gnModel.getProperty(gn, "countryCode"), total_casos.getPais().getIso_code())
                    .addProperty(schemaModel.getProperty(schema, "latitude"), total_casos.getPais().getLatitud())
                    .addProperty(schemaModel.getProperty(schema, "longitude"), total_casos.getPais().getLongitud())
                    .addProperty(dboModel.getProperty(dbo, "populationTotal"), total_casos.getPais().getPoblacion())
                    .addProperty(dboModel.getProperty(dbo, "grossDomesticProductNominalPerCapita"),total_casos.getPais().getPer_capita());
                    
                    con.addProperty(dboModel.getProperty(dbo, "country"),rC);

            String dataset2 = total_casos.getDs().getNombre().replaceAll(" ", "_");
            Resource dat2 = model.createResource(dataPrefix + dataset2)
                    .addProperty(RDF.type, dcatModel.getProperty(dcat, "Dataset"))
                    .addProperty(dboModel.getProperty(dbo, "name"), total_casos.getDs().getNombre())
                    .addProperty(dboModel.getProperty(dbo, "description"), total_casos.getDs().getDescripcion())
                    .addProperty(dcatModel.getProperty(dcat, "downloadURL"), total_casos.getDs().getUrl());
                    //.addProperty(dcModel.getProperty(dc, "dateSubmited"), String.valueOf(lstcon.getDataset().getSubmited()))
                   // .addProperty(dcatModel.getProperty(dcat, "dateModification"), String.valueOf(lstcon.getDataset().getModificaction()));
            //cat2.addProperty(dcatModel.getProperty(dcat, "dataset"), dat2);
                  
            /*Confirmed*/
            
            Resource conf = model.createResource(dataPrefix + total_casos.getCode())
                    .addProperty(RDF.type, newOntoM.getProperty(newOnto, "Confirmed_Cases"))
                    .addProperty(schemaModel.getProperty(schema, "observationDate"), total_casos.getFecha())
                    .addProperty(newOntoM.getProperty(newOnto, "quantity"), total_casos.getNewcasos())
                    .addProperty(newOntoM.getProperty(newOnto, "totalQuantity"), total_casos.getTotalcasos());
            conf.addProperty(gnModel.getProperty(gn, "locatedIn"), rC);
            conf.addProperty(provModel.getProperty(prov, "wasDerivedFrom"), dat2);
            
            
            Resource act = model.createResource(dataPrefix + total_casos.getTa().getCode())
                    .addProperty(RDF.type, newOntoM.getProperty(newOnto, "Active_Cases"))
                    .addProperty(schemaModel.getProperty(schema, "observationDate"), total_casos.getFecha())
                    
                    .addProperty(newOntoM.getProperty(newOnto, "totalQuantity"), total_casos.getTa().getActivos());
            act.addProperty(gnModel.getProperty(gn, "locatedIn"), rC);
            act.addProperty(provModel.getProperty(prov, "wasDerivedFrom"), dat2);
            
            
            Resource mue = model.createResource(dataPrefix + total_casos.getTm().getCode())
                    .addProperty(RDF.type, newOntoM.getProperty(newOnto, "Muerte_Cases"))
                    .addProperty(schemaModel.getProperty(schema, "observationDate"), total_casos.getFecha())
                    .addProperty(newOntoM.getProperty(newOnto, "quantity"), total_casos.getTm().getNew_muertes())
                    .addProperty(newOntoM.getProperty(newOnto, "totalQuantity"), total_casos.getTm().getTotal_muertes());
            mue.addProperty(gnModel.getProperty(gn, "locatedIn"), rC);
            mue.addProperty(provModel.getProperty(prov, "wasDerivedFrom"), dat2);
            
            
            
             Resource hosp = model.createResource(dataPrefix + total_casos.getTh().getCode())
                    .addProperty(RDF.type, newOntoM.getProperty(newOnto, "Hospitalizados"))
                    .addProperty(schemaModel.getProperty(schema, "observationDate"), total_casos.getFecha())
                    
                    .addProperty(newOntoM.getProperty(newOnto, "totalQuantity"), total_casos.getTh().getTotal_hospitalizados());
            hosp.addProperty(gnModel.getProperty(gn, "locatedIn"), rC);
            hosp.addProperty(provModel.getProperty(prov, "wasDerivedFrom"), dat2);
            
            
            
             Resource recu = model.createResource(dataPrefix + total_casos.getTr().getCode())
                    .addProperty(RDF.type, newOntoM.getProperty(newOnto, "Recuperados"))
                    .addProperty(schemaModel.getProperty(schema, "observationDate"), total_casos.getFecha())
                    
                    .addProperty(newOntoM.getProperty(newOnto, "totalQuantity"), total_casos.getTr().getTotal_recuperados());
            recu.addProperty(gnModel.getProperty(gn, "locatedIn"), rC);
            recu.addProperty(provModel.getProperty(prov, "wasDerivedFrom"), dat2);
            
            
            
            
            Resource prue = model.createResource(dataPrefix + total_casos.getTp().getCode())
                    .addProperty(RDF.type, newOntoM.getProperty(newOnto, "Pruebas"))
                    .addProperty(schemaModel.getProperty(schema, "observationDate"), total_casos.getFecha())
                    .addProperty(newOntoM.getProperty(newOnto, "quantity"), total_casos.getTp().getNew_pruebas())
                    .addProperty(newOntoM.getProperty(newOnto, "totalQuantity"), total_casos.getTp().getTotal_pruebas());
            prue.addProperty(gnModel.getProperty(gn, "locatedIn"), rC);
            prue.addProperty(provModel.getProperty(prov, "wasDerivedFrom"), dat2);
            
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
        //model.write(System.out);
        //model.write(System.out, "N3");
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


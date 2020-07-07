/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author Estevan
 */
public class casos {

    private String continent;
    private String iso_code;
    private String country;
    private double longitude;
    private double latitude;
    private String date;
    private int total_case;
    private int new_cases;
    private int total_deaths;
    private int new_deaths;
    private int total_tests;
    private int new_tests;
    private int positives;
    private int negatives;
    private int realized;
    private int not_confirmed;
    private int active_caso;
    private int recovered;
    private int confirmed;
    private int hospitalized;
    private int population;
    private int gdp_per_capita;

    public casos(int new_cases) {
        this.new_cases = new_cases;
    }
    
    public casos(String continent){
        this.continent=continent;
    }
    
    

    public casos(String continent, String iso_code, String country, double longitude, double latitude, String date, int total_case, int new_cases, int total_deaths, int new_deaths, int total_tests, int new_tests, int positives, int negatives, int realized, int not_confirmed, int active_caso, int recovered, int confirmed, int hospitalized, int population, int gdp_per_capita) {
        this.continent = continent;
        this.iso_code = iso_code;
        this.country = country;
        this.longitude = longitude;
        this.latitude = latitude;
        this.date = date;
        this.total_case = total_case;
        this.new_cases = new_cases;
        this.total_deaths = total_deaths;
        this.new_deaths = new_deaths;
        this.total_tests = total_tests;
        this.new_tests = new_tests;
        this.positives = positives;
        this.negatives = negatives;
        this.realized = realized;
        this.not_confirmed = not_confirmed;
        this.active_caso = active_caso;
        this.recovered = recovered;
        this.confirmed = confirmed;
        this.hospitalized = hospitalized;
        this.population = population;
        this.gdp_per_capita = gdp_per_capita;
    }

  

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getIso_code() {
        return iso_code;
    }

    public void setIso_code(String iso_code) {
        this.iso_code = iso_code;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotal_case() {
        return total_case;
    }

    public void setTotal_case(int total_case) {
        this.total_case = total_case;
    }

    public int getNew_cases() {
        return new_cases;
    }

    public void setNew_cases(int new_cases) {
        this.new_cases = new_cases;
    }

    public int getTotal_deaths() {
        return total_deaths;
    }

    public void setTotal_deaths(int total_deaths) {
        this.total_deaths = total_deaths;
    }

    public int getNew_deaths() {
        return new_deaths;
    }

    public void setNew_deaths(int new_deaths) {
        this.new_deaths = new_deaths;
    }

    public int getTotal_tests() {
        return total_tests;
    }

    public void setTotal_tests(int total_tests) {
        this.total_tests = total_tests;
    }

    public int getNew_tests() {
        return new_tests;
    }

    public void setNew_tests(int new_tests) {
        this.new_tests = new_tests;
    }

    public int getPositives() {
        return positives;
    }

    public void setPositives(int positives) {
        this.positives = positives;
    }

    public int getNegatives() {
        return negatives;
    }

    public void setNegatives(int negatives) {
        this.negatives = negatives;
    }

    public int getRealized() {
        return realized;
    }

    public void setRealized(int realized) {
        this.realized = realized;
    }

    public int getNot_confirmed() {
        return not_confirmed;
    }

    public void setNot_confirmed(int not_confirmed) {
        this.not_confirmed = not_confirmed;
    }

    public int getActive_caso() {
        return active_caso;
    }

    public void setActive_caso(int active_caso) {
        this.active_caso = active_caso;
    }

    public int getRecovered() {
        return recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public int getHospitalized() {
        return hospitalized;
    }

    public void setHospitalized(int hospitalized) {
        this.hospitalized = hospitalized;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getGdp_per_capita() {
        return gdp_per_capita;
    }

    public void setGdp_per_capita(int gdp_per_capita) {
        this.gdp_per_capita = gdp_per_capita;
    }

    @Override
    public String toString() {
        return "casos{" + "continent=" + continent + ", iso_code=" + iso_code + ", country=" + country + ", longitude=" + longitude + ", latitude=" + latitude + ", date=" + date + ", total_case=" + total_case + ", new_cases=" + new_cases + ", total_deaths=" + total_deaths + ", new_deaths=" + new_deaths + ", total_tests=" + total_tests + ", new_tests=" + new_tests + ", positives=" + positives + ", negatives=" + negatives + ", realized=" + realized + ", not_confirmed=" + not_confirmed + ", active_caso=" + active_caso + ", recovered=" + recovered + ", confirmed=" + confirmed + ", hospitalized=" + hospitalized + ", population=" + population + ", gdp_per_capita=" + gdp_per_capita + '}';
    }

    
    
    
}

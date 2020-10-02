package com.example.countries.controllers;

import com.example.countries.models.Country;
import com.example.countries.repositories.CountryReposiory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CountryController {

    @Autowired
    private CountryReposiory countryrepo;

    private List<Country> findCountries(List<Country> myList, CheckCountry tester) {
        List<Country> tempList = new ArrayList<>();

        for (Country c : myList) {
            if (tester.test(c)) {
                tempList.add(c);
            }
        }

        return tempList;
    }

    @GetMapping(value = "/names/all", produces = {"application/json"})
    public ResponseEntity<?> listAllCountries() {
        List<Country> myList = new ArrayList<>();

        countryrepo.findAll().iterator().forEachRemaining(myList::add);

        myList.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));

        for (Country c : myList) {
            System.out.println(c);
        }
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    @GetMapping(value = "/names/start/{letter}", produces = {"application/json"})
    public ResponseEntity<?> listAllByName(@PathVariable char letter) {
        List<Country> myList = new ArrayList<>();
        countryrepo.findAll().iterator().forEachRemaining(myList::add);

        List<Country> rtnList = findCountries(myList, c -> c.getName().charAt(0) == Character.toUpperCase(letter));

        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }

    @GetMapping(value = "/population/total", produces = {"application/json"})
    public ResponseEntity<?> totalPopulation() {
        List<Country> countryList = new ArrayList<>();

        countryrepo.findAll().iterator().forEachRemaining(countryList::add);

        long total = 0;
        for (Country c : countryList) {
            total += c.getPopulation();
        }

        System.out.println("The Total Population is " + total);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/population/min", produces = {"application/json"})
    public ResponseEntity<?> minPopulation() {
        List<Country> countryList = new ArrayList<>();

        countryrepo.findAll().iterator().forEachRemaining(countryList::add);

        countryList.sort((c1, c2) -> (int) c1.getPopulation() - (int) c2.getPopulation());

        return new ResponseEntity<>(countryList.get(0), HttpStatus.OK);
    }

    @GetMapping(value = "/population/max", produces = {"application/json"})
    public ResponseEntity<?> maxPopulation() {
        List<Country> countryList = new ArrayList<>();

        countryrepo.findAll().iterator().forEachRemaining(countryList::add);

        countryList.sort((c1, c2) -> (int) c2.getPopulation() - (int) c1.getPopulation());

        return new ResponseEntity<>(countryList.get(0), HttpStatus.OK);
    }

    @GetMapping(value = "/population/median", produces = {"application/json"})
    public ResponseEntity<?> medianPopulation() {
        List<Country> countryList = new ArrayList<>();

        countryrepo.findAll().iterator().forEachRemaining(countryList::add);

        countryList.sort((c1, c2) -> (int) c1.getPopulation() - (int) c2.getPopulation());

        double median;

        if (countryList.size() % 2 == 0) {
            median = ((double)(countryList.size()/2) + (double)((countryList.size()/2)-1))/2;
        } else {
            median = (int) Math.floor(countryList.size()/2);
        }

        System.out.println(median);

        return new ResponseEntity<>(countryList.get((int) median + 1), HttpStatus.OK);
    }
}

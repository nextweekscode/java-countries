package com.example.countries.repositories;

import com.example.countries.models.Country;
import org.springframework.data.repository.CrudRepository;

public interface CountryReposiory extends CrudRepository<Country, Long> {
}

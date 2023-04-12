package com.astamatii.endava.webchat.repositories;

import com.astamatii.endava.webchat.models.City;
import com.astamatii.endava.webchat.models.Language;
import com.astamatii.endava.webchat.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByUsername(String username);
    Optional<Person> findByEmail(String email);
    void deleteByUsername(String username);

//    @Modifying
//    @Query("INSERT INTO Person (name, username, email, password) VALUES (:name, :username, :password, :email)")
//    void registerPerson(@Param("name") String name, @Param("username") String username, @Param("email") String email,
//                        @Param("password") String password);
//
//    @Modifying
//    @Query("UPDATE Person p SET p.name = :name, p.dob:dob, p.language.id = :languageId, p.city.id:cityId, p.textColor:textColor WHERE p.id = :id)")
//    void updateProfileByIdWithValues(@Param("id") Long id, @Param("name") String name, @Param("dob") LocalDate dob,
//                                     @Param("languageId") Long languageId, @Param("cityId") Long cityId, @Param("textColor") String textColor);
//
//    @Modifying
//    @Query("UPDATE Person p SET p.name = :name, p.dob:dob, p.language = :language, p.city:city, p.textColor:textColor WHERE p.id = :id)")
//    void updateProfileByIdWithPersonValues(@Param("id") Long id, @Param("name") String name, @Param("dob") LocalDate dob,
//                                           @Param("language") Language language, @Param("city") City city, @Param("textColor") String textColor);
//
//    @Modifying
//    @Query("UPDATE Person p SET p.password = :password WHERE p.id = :id")
//    void updatePasswordById(@Param("id") Long id, @Param("password") String password);
//
//    @Modifying
//    @Query("UPDATE Person p SET p.username = :username WHERE p.id = :id")
//    void updateUsernameById(@Param("id") Long id, @Param("username") String username);
//
//    @Modifying
//    @Query("UPDATE Person p SET p.email = :email WHERE p.id = :id")
//    void updateEmailById(@Param("id") Long id, @Param("email") String email);

//    @Query("SELECT p FROM Person p WHERE p.email = :email LIMIT 1")
//    Optional<Person> findEmployeesByAgeGreaterThan(@Param("email") String email);
}

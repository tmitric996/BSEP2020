package com.example.bsep2020.model;
import javax.persistence.*;
import java.util.regex.*;
import com.example.bsep2020.enumeration.EntityType;
import javax.validation.constraints.*;
import lombok.*;
/*
 digitalni entitet, odnosno podaci o domenu kom ce se izdavati sertifikat 
 ili koji ce izdavati sertifikat
 */
@Data
@Entity
@Table(name="digEntity")
public class DigEntity {
	@Id
	@Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	//@NotNull(message = "Obavezno polje!")
    private Long id;
	@Column
	//@NotNull(message = "Obavezno polje!")
	public String commonName;
	@Enumerated
	//@NotNull(message = "Obavezno polje!")
	public EntityType entityType;
	//zavisno kog je tipa entitet neka polja ce biti prazna...ali neka polja ce uvek biti popunjena poput commonname cn i sl
	@Column
	//@NotNull
	private String surname;
	@Column
	private String givenName;
	@Column
	//@NotNull
	private String organization;
	@Column
	//@NotNull(message = "Obavezno polje!")
	private String organizationUnitName;
	@Column
	//@Size(max = 2, message = "Potrebno je uneti samo dva slova!")
	private String countryCode;
	//   email address in Verisign certificates
	@Column
	//@Email(message = "Unesite validnu email adresu!")
	private String email;
	public DigEntity() {
		super();
	}
	public DigEntity(Long id, String commonName, EntityType entityType, String surname, String givenName,
			String organization, String organizationUnitName, String countryCode, String email) {
		super();
		this.id = id;
		this.commonName = commonName;
		this.entityType = entityType;
		this.surname = surname;
		this.givenName = givenName;
		this.organization = organization;
		this.organizationUnitName = organizationUnitName;
		this.countryCode = countryCode;
		this.email = email;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCommonName() {
		return commonName;
	}
	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}
	public EntityType getEntityType() {
		return entityType;
	}
	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getGivenName() {
		return givenName;
	}
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getOrganizationUnitName() {
		return organizationUnitName;
	}
	public void setOrganizationUnitName(String organizationUnitName) {
		this.organizationUnitName = organizationUnitName;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
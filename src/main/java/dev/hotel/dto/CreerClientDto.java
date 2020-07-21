package dev.hotel.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreerClientDto {
	
	@NotBlank
	@NotNull
	@Size(min = 2)
	@JsonProperty("nom")
	private String nom;
	@NotBlank
	@NotNull
	@Size(min = 2)
	@JsonProperty("prenoms")
    private String prenoms;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenoms() {
        return prenoms;
    }

    public void setPrenoms(String prenoms) {
        this.prenoms = prenoms;
    }

}

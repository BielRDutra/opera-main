package com.operalatam.api.dto;

public class ListAeronavesRequestDTO {
	private Integer page;
	private Integer size;
	private String nome;

	public ListAeronavesRequestDTO() {}

	public ListAeronavesRequestDTO(Integer page, Integer size, String nome) {
		this.page = page;
		this.size = size;
		this.nome = nome;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}

package com.process.demo.model;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "ropa")
public class Ropa implements LocalEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(nullable = false)
	private String name;
	
//	@Column
//	private String description;
//
//	@Column(name = "created_by")
//	private String createdBy;
//	
//	@Column(name = "created_at")
//	private Timestamp createdAt;
//
//	@Column(name = "updated_by")
//	private String updatedBy;
//	
//	@Column(name = "updated_at")
//	@Version
//	private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
    
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Ropa [id=").append(id).append(", name=").append(name).append(", owner=").append(owner).append("]");
        return builder.toString();
    }
//	public String getDescription() {
//		return description;
//	}
//
//	public void setDescription(String description) {
//		this.description = description;
//	}
//
//	public String getCreatedBy() {
//		return createdBy;
//	}
//
//	public void setCreatedBy(String createdBy) {
//		this.createdBy = createdBy;
//	}
//
//	public Timestamp getCreatedAt() {
//		return createdAt;
//	}
//
//	public void setCreatedAt(Timestamp createdAt) {
//		this.createdAt = createdAt;
//	}
//
//	public String getUpdatedBy() {
//		return updatedBy;
//	}
//
//	public void setUpdatedBy(String updatedBy) {
//		this.updatedBy = updatedBy;
//	}
//
//	public Timestamp getUpdatedAt() {
//		return updatedAt;
//	}
//
//	public void setUpdatedAt(Timestamp updatedAt) {
//		this.updatedAt = updatedAt;
//	}

}

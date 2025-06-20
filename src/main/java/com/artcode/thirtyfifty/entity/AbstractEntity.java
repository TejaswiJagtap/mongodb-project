package com.artcode.thirtyfifty.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
//@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@ToString
@Builder
public class AbstractEntity implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@CreatedBy
	@Column(name = "created_by",columnDefinition = "bigint default 1", updatable = false)
	public String createdBy;
	
	@CreatedBy
	@Column(name = "created_by_user_name",columnDefinition = "varchar(255) default ''", updatable = false)
	public String createdByUserName;
	
	@LastModifiedBy
	@Column(name = "last_modified_by",columnDefinition = "bigint default 1")
	public Long lastModifiedBy;
	
	@LastModifiedBy
	@Column(name = "last_modified_by_user_name",columnDefinition = "varchar(255) default ''")
	public String lastModifiedByUserName;
 
	
	@JsonIgnore
	@Column(name="created_date", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@CreationTimestamp
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime createdDate;
	
	@JsonIgnore
	@Column(name="last_modified_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@UpdateTimestamp
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime lastModifiedDate; 
	
	@Column(name = "status", columnDefinition = "varchar(32) default 'ACTIVE'")
	public String status;
	
	@Column(name = "is_deleted",columnDefinition = "boolean default false")
	public Boolean isDeleted;
	
	
	@PrePersist
    void preInsert() {
        if (this.status == null) {
            this.status = "ACTIVE";
        }
        
        if (this.isDeleted == null) {
            this.isDeleted = false;
        }
        
       
    }
}

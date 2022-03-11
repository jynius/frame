/**
 * 
 */
package us.jyni.frame.jpa;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

/**
 * @author jynius
 *
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public class BaseEntity<T> implements Serializable {

	private static final long serialVersionUID = -7681747208884123731L;

	@Id
	private T id;

	@CreatedBy
	private String createdBy;
	
	@CreatedDate
	private LocalDateTime createdDate;
	
	@LastModifiedBy
	private String modifiedBy;
	
	@LastModifiedDate
	private LocalDateTime modifiedDate;
}

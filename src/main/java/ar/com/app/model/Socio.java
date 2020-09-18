package ar.com.app.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@XmlRootElement
@NamedQueries(@NamedQuery(name = "findSocioByDocumento", query = "SELECT s FROM Socio s WHERE s.documento = :custDocumento"))
public class Socio implements Serializable {

	private static final long serialVersionUID = 1L;

	private final static Logger logger = LoggerFactory.getLogger(Socio.class);
	public final static String QUERY_BY_DOCUMENTO = "findSocioByDocumento";
	public final static String QUERY_BY_DOCUMENTO_PARAM = "custDocumento";
	public final static String FILTRO_DOCUMENTO = "documento";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	@Version
	@Column(name = "version")
	private int version;

	@Column
	@NotNull(message = "El campo Nombre es requerido.")
    @Size(min = 1, max = 25)
    @Pattern(regexp = "[A-Za-z ]*", message = "Sólo letras y espacios están permitidos.")
	private String nombre;

	@Column
	@NotNull(message = "El campo Plan es requerido.")
	private String plan;

	@Column
	@NotNull(message = "El campo Nacimiento es requerido.")
	private Date nacimiento;

	@Column
	@NotNull(message = "El campo Nombre es requerido.")
	private String filial;

	@Column
	@NotNull(message = "El campo Alta es requerido.")
	private Date alta;

	@Column
	@NotNull(message = "El campo Tipo es requerido.")
	private String tipo;

	@Column
	private Date baja;

	@Column
	@NotNull(message = "El campo Documento es requerido.")
	private String documento;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Socio)) {
			return false;
		}
		Socio other = (Socio) obj;
		if (id != null) {
			if (!id.equals(other.id)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public Date getNacimiento() {
		return nacimiento;
	}

	public void setNacimiento(Date nacimiento) {
		this.nacimiento = nacimiento;
	}

	public String getFilial() {
		return filial;
	}

	public void setFilial(String filial) {
		this.filial = filial;
	}

	public Date getAlta() {
		return alta;
	}

	public void setAlta(Date alta) {
		this.alta = alta;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Date getBaja() {
		return baja;
	}

	public void setBaja(Date baja) {
		this.baja = baja;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (nombre != null && !nombre.trim().isEmpty())
			result += "nombre: " + nombre;
		if (plan != null && !plan.trim().isEmpty())
			result += ", plan: " + plan;
		if (filial != null && !filial.trim().isEmpty())
			result += ", filial: " + filial;
		if (tipo != null && !tipo.trim().isEmpty())
			result += ", tipo: " + tipo;
		if (documento != null && !documento.trim().isEmpty())
			result += ", documento: " + documento;
		return result;
	}

    @PrePersist
    public void prePersist() {
        logger.info("Pre persist Socio");
    }

    @PreUpdate
    public void preUpdate() {
    	logger.info("Pre update Socio");
    }
}

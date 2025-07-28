package com.infinite.jsf.provider.model;
 
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.infinite.jsf.recipient.model.Recipient;
 
public class MedicalProcedure implements Serializable {
 
    private String procedureId;
    private Appointment appointment;
    private Recipient recipient;
    private Provider provider;
    private Doctors doctor;
 
    private Date scheduledDate;      // NEW: for procedures planned ahead
    private Date procedureDate;      // actual date
    private Date fromDate;           // long-term start
    private Date toDate;             // long-term end
 
    private String diagnosis;
    private String recommendations;
 
    private ProcedureStatus procedureStatus;  // NEW: 'scheduled', 'in_progress', 'completed', 'cancelled'
    private Date createdAt;
    private ProcedureType type;
    
    private Set<Prescription> prescriptions;
    private Set<ProcedureDailyLog> logs;
    
    public ProcedureType getType() {
		return type;
	}
 
	public void setType(ProcedureType type) {
		this.type = type;
	}
 
    public Set<ProcedureDailyLog> getLogs() {
		return logs;
	}
 
	public void setLogs(Set<ProcedureDailyLog> logs) {
		this.logs = logs;
	}
 
    public MedicalProcedure() {
        this.appointment = new Appointment();
        this.recipient = new Recipient();
        this.provider = new Provider();
        this.doctor = new Doctors();
    }
 
    // Getters and Setters
    public String getProcedureId() {
        return procedureId;
    }
 
    public void setProcedureId(String procedureId) {
        this.procedureId = procedureId;
    }
 
    public Appointment getAppointment() {
        return appointment;
    }
 
    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
 
    public Recipient getRecipient() {
        return recipient;
    }
 
    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }
 
    public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public Doctors getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctors doctor) {
		this.doctor = doctor;
	}

	public Date getScheduledDate() {
        return scheduledDate;
    }
 
    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }
 
    public Date getProcedureDate() {
        return procedureDate;
    }
 
    public void setProcedureDate(Date procedureDate) {
        this.procedureDate = procedureDate;
    }
 
    public Date getFromDate() {
        return fromDate;
    }
 
    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }
 
    public Date getToDate() {
        return toDate;
    }
 
    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
 
    public String getDiagnosis() {
        return diagnosis;
    }
 
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
 
    public String getRecommendations() {
        return recommendations;
    }
 
    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }
 
    public ProcedureStatus getProcedureStatus() {
		return procedureStatus;
	}
 
	public void setProcedureStatus(ProcedureStatus procedureStatus) {
		this.procedureStatus = procedureStatus;
	}
 
	public Date getCreatedAt() {
        return createdAt;
    }
 
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
 
    public Set<Prescription> getPrescriptions() {
		return prescriptions;
	}

	public void setPrescriptions(Set<Prescription> prescriptions) {
		this.prescriptions = prescriptions;
	}

	@Override
    public String toString() {
        return "MedicalProcedure [" +
                "procedureId=" + procedureId +
                ", appointmentId=" + (appointment != null ? appointment.getAppointmentId() : null) +
                ", recipientId=" + (recipient != null ? recipient.gethId() : null) +
                ", providerId=" + (provider != null ? provider.getProviderId() : null) +
                ", doctorId=" + (doctor != null ? doctor.getDoctorId() : null) +
                ", scheduledDate=" + scheduledDate +
                ", procedureDate=" + procedureDate +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", diagnosis=" + diagnosis +
                ", recommendations=" + recommendations +
                ", procedureStatus=" + procedureStatus +
                ", createdAt=" + createdAt +
//                ", claimsCount=" + (claims != null ? claims.size() : 0) +
                ", prescriptionsCount=" + (prescriptions != null ? prescriptions.size() : 0) +
                ", logsCount=" + (logs != null ? logs.size() : 0) +
                "]";
    }

}
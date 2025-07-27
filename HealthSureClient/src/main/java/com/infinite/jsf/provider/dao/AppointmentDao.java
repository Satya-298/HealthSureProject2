package com.infinite.jsf.provider.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.infinite.jsf.provider.model.Appointment;
import com.infinite.jsf.provider.model.AppointmentStatus;

public interface AppointmentDao {
	
    List<Appointment> getAppointmentsByDoctorAndStatus(String doctorId, String status);

    boolean markAppointmentAsBooked(String appointmentId, Timestamp bookedAt);

    boolean cancelPendingAppointmentByDoctor(String appointmentId, Timestamp cancelledAt);
    
    List<Appointment> getCompletedAppointments();

	Appointment getAppointmentById(String appointmentId);

	List<Appointment> getAllAppointments();

}

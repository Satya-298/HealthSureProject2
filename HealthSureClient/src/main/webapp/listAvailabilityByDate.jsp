<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<html>
<head>
    <title>Doctor Availability by Date</title>
    <style>
        body {
            background-color: #f3f4f6;
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 24px;
        }
        .form-container {
            background: white;
            padding: 24px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            max-width: 1200px;
            margin: auto;
        }
        h2 {
            text-align: center;
            color: #1d4ed8;
            margin-bottom: 24px;
        }
        .centered-input-block {
            display: flex;
            flex-direction: column;
            align-items: center;
            margin-bottom: 20px;
        }
        label {
            font-weight: bold;
            margin-bottom: 6px;
        }
        input[type="date"], input[type="text"] {
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-size: 14px;
        }
        .button-group {
            margin-top: 16px;
            display: flex;
            gap: 12px;
        }
        .btn {
            padding: 10px 16px;
            border-radius: 6px;
            border: none;
            font-weight: bold;
            cursor: pointer;
        }
        .btn-blue {
            background-color: #2563eb;
            color: white;
        }
        .btn-blue:hover {
            background-color: #1e40af;
        }
        .btn-gray {
            background-color: #6b7280;
            color: white;
        }
        .btn-gray:hover {
            background-color: #374151;
        }
        .error {
            color: red;
            margin-top: 4px;
            font-size: 13px;
        }
        .table-wrapper {
            overflow-x: auto;
            margin-top: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            text-align: center;
            background-color: white;
        }
        th, td {
            padding: 10px;
            border-bottom: 1px solid #ddd;
            white-space: nowrap;
        }
        th {
            background-color: #f9fafb;
            color: #2563eb;
        }
        .sort-header {
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .sort-icons {
            display: inline-flex;
            flex-direction: column;
            margin-left: 5px;
        }
        .sort-icon {
            font-size: 0.7em;
            line-height: 1;
            cursor: pointer;
        }
        .sort-icon:hover {
            color: #007bff;
        }
        .pagination-panel {
            margin-top: 24px;
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 12px;
        }
        .message {
            text-align: center;
            color: red;
            margin-top: 16px;
        }
        .btn-update {
            background-color: #28a745;
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 4px;
            cursor: pointer;
            font-weight: bold;
        }
        .btn-update:hover {
            background-color: #218838;
        }
        .delete-button {
            background-color: #dc3545;
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 4px;
            cursor: pointer;
            font-weight: bold;
        }
        .delete-button:hover {
            background-color: #c82333;
        }
    </style>

    <script>
        window.addEventListener('load', function () {
            const calendarInput = document.querySelector("#availabilityForm\\:date");
            if (calendarInput) {
                calendarInput.setAttribute("type", "date");
                const today = new Date().toISOString().split("T")[0];
                calendarInput.setAttribute("min", today);
            }
        });
    </script>
</head>

<body>
<f:view>
<h:form id="availabilityForm" styleClass="form-container">
    <h2>Search Availability by Date</h2>

    <div class="centered-input-block">
        <h:outputLabel for="date" value="Select Date:" />
        <h:inputText id="date" value="#{availabilityController.selectedDate}">
            <f:convertDateTime pattern="yyyy-MM-dd" />
        </h:inputText>
        <h:message for="date" styleClass="error" />

        <div class="button-group">
            <h:commandButton value="Search" action="#{availabilityController.fetchAvailabilityByDate}" styleClass="btn btn-blue" />
            <h:commandButton value="Reset" action="#{availabilityController.resetSearchDateForm}" styleClass="btn btn-gray" />
            <h:commandButton value="Back" action="#{availabilityController.resetAddFormBackButton}" immediate="true" styleClass="btn btn-gray" />
        </div>
    </div>

    <h:panelGroup rendered="#{not empty availabilityController.availabilityByDateList}">
        <div class="table-wrapper">
          <h:outputText value="Total Records: #{availabilityController.totalRecords}" styleClass="total-records" />
            <h:dataTable value="#{availabilityController.paginatedList}" var="avail" border="1">
                <h:column>
                    <f:facet name="header">
                        <h:panelGroup layout="block" style="display: flex; align-items: center; justify-content: center;">
                            <h:outputText value="Availability ID" />
                            <h:panelGroup styleClass="sort-icons">
                                <h:commandLink action="#{availabilityController.sortByAsc('availabilityId')}" 
                                               rendered="#{!(availabilityController.sortColumn eq 'availabilityId' and availabilityController.sortAscending)}" 
                                               styleClass="sort-icon">▲</h:commandLink>
                                <h:commandLink action="#{availabilityController.sortByDesc('availabilityId')}" 
                                               rendered="#{!(availabilityController.sortColumn eq 'availabilityId' and not availabilityController.sortAscending)}" 
                                               styleClass="sort-icon">▼</h:commandLink>
                            </h:panelGroup>
                        </h:panelGroup>
                    </f:facet>
                    <h:outputText value="#{avail.availabilityId}" />
                </h:column>

                <h:column>
                    <f:facet name="header">
                        <h:panelGroup layout="block" style="display: flex; align-items: center; justify-content: center;">
                            <h:outputText value="Doctor" />
                            <h:panelGroup styleClass="sort-icons">
                                <h:commandLink action="#{availabilityController.sortByAsc('doctorName')}" 
                                               rendered="#{!(availabilityController.sortColumn eq 'doctorName' and availabilityController.sortAscending)}" 
                                               styleClass="sort-icon">▲</h:commandLink>
                                <h:commandLink action="#{availabilityController.sortByDesc('doctorName')}" 
                                               rendered="#{!(availabilityController.sortColumn eq 'doctorName' and not availabilityController.sortAscending)}" 
                                               styleClass="sort-icon">▼</h:commandLink>
                            </h:panelGroup>
                        </h:panelGroup>
                    </f:facet>
                    <h:outputText value="#{avail.doctor.doctorName}" />
                </h:column>

                <h:column>
                    <f:facet name="header">
                        <h:panelGroup layout="block" style="display: flex; align-items: center; justify-content: center;">
                            <h:outputText value="Specialization" />
                            <h:panelGroup styleClass="sort-icons">
                                <h:commandLink action="#{availabilityController.sortByAsc('specialization')}" 
                                               rendered="#{!(availabilityController.sortColumn eq 'specialization' and availabilityController.sortAscending)}" 
                                               styleClass="sort-icon">▲</h:commandLink>
                                <h:commandLink action="#{availabilityController.sortByDesc('specialization')}" 
                                               rendered="#{!(availabilityController.sortColumn eq 'specialization' and not availabilityController.sortAscending)}" 
                                               styleClass="sort-icon">▼</h:commandLink>
                            </h:panelGroup>
                        </h:panelGroup>
                    </f:facet>
                    <h:outputText value="#{avail.doctor.specialization}" />
                </h:column>

                <h:column>
                    <f:facet name="header">
                        <h:panelGroup layout="block" style="display: flex; align-items: center; justify-content: center;">
                            <h:outputText value="Available Date" />
                            <h:panelGroup styleClass="sort-icons">
                                <h:commandLink action="#{availabilityController.sortByAsc('availableDate')}" 
                                               rendered="#{!(availabilityController.sortColumn eq 'availableDate' and availabilityController.sortAscending)}" 
                                               styleClass="sort-icon">▲</h:commandLink>
                                <h:commandLink action="#{availabilityController.sortByDesc('availableDate')}" 
                                               rendered="#{!(availabilityController.sortColumn eq 'availableDate' and not availabilityController.sortAscending)}" 
                                               styleClass="sort-icon">▼</h:commandLink>
                            </h:panelGroup>
                        </h:panelGroup>
                    </f:facet>
                    <h:outputText value="#{avail.availableDate}">
                        <f:convertDateTime pattern="dd-MM-yyyy" />
                    </h:outputText>
                </h:column>

                <h:column>
                    <f:facet name="header">
                        <h:panelGroup layout="block" style="display: flex; align-items: center; justify-content: center;">
                            <h:outputText value="Start Time" />
                            <h:panelGroup styleClass="sort-icons">
                                <h:commandLink action="#{availabilityController.sortByAsc('startTime')}" 
                                               rendered="#{!(availabilityController.sortColumn eq 'startTime' and availabilityController.sortAscending)}" 
                                               styleClass="sort-icon">▲</h:commandLink>
                                <h:commandLink action="#{availabilityController.sortByDesc('startTime')}" 
                                               rendered="#{!(availabilityController.sortColumn eq 'startTime' and not availabilityController.sortAscending)}" 
                                               styleClass="sort-icon">▼</h:commandLink>
                            </h:panelGroup>
                        </h:panelGroup>
                    </f:facet>
                    <h:outputText value="#{avail.startTime}" >
                        <f:convertDateTime pattern="HH:mm" />
                    </h:outputText>
                </h:column>

                <h:column>
                    <f:facet name="header">
                        <h:panelGroup layout="block" style="display: flex; align-items: center; justify-content: center;">
                            <h:outputText value="End Time" />
                            <h:panelGroup styleClass="sort-icons">
                                <h:commandLink action="#{availabilityController.sortByAsc('endTime')}" 
                                               rendered="#{!(availabilityController.sortColumn eq 'endTime' and availabilityController.sortAscending)}" 
                                               styleClass="sort-icon">▲</h:commandLink>
                                <h:commandLink action="#{availabilityController.sortByDesc('endTime')}" 
                                               rendered="#{!(availabilityController.sortColumn eq 'endTime' and not availabilityController.sortAscending)}" 
                                               styleClass="sort-icon">▼</h:commandLink>
                            </h:panelGroup>
                        </h:panelGroup>
                    </f:facet>
                    <h:outputText value="#{avail.endTime}" >
                        <f:convertDateTime pattern="HH:mm" />
                    </h:outputText>
                </h:column>

                <h:column>
                    <f:facet name="header">
                        <h:panelGroup layout="block" style="display: flex; align-items: center; justify-content: center;">
                            <h:outputText value="Slot Type" />
                            <h:panelGroup styleClass="sort-icons">
                                <h:commandLink action="#{availabilityController.sortByAsc('slotType')}" 
                                               rendered="#{!(availabilityController.sortColumn eq 'slotType' and availabilityController.sortAscending)}" 
                                               styleClass="sort-icon">▲</h:commandLink>
                                <h:commandLink action="#{availabilityController.sortByDesc('slotType')}" 
                                               rendered="#{!(availabilityController.sortColumn eq 'slotType' and not availabilityController.sortAscending)}" 
                                               styleClass="sort-icon">▼</h:commandLink>
                            </h:panelGroup>
                        </h:panelGroup>
                    </f:facet>
                    <h:outputText value="#{avail.slotType}" />
                </h:column>

                <h:column>
                    <f:facet name="header">
                        <h:panelGroup layout="block" style="display: flex; align-items: center; justify-content: center;">
                            <h:outputText value="Recurring" />
                            <h:panelGroup styleClass="sort-icons">
                                <h:commandLink action="#{availabilityController.sortByAsc('recurring')}" 
                                               rendered="#{!(availabilityController.sortColumn eq 'recurring' and availabilityController.sortAscending)}" 
                                               styleClass="sort-icon">▲</h:commandLink>
                                <h:commandLink action="#{availabilityController.sortByDesc('recurring')}" 
                                               rendered="#{!(availabilityController.sortColumn eq 'recurring' and not availabilityController.sortAscending)}" 
                                               styleClass="sort-icon">▼</h:commandLink>
                            </h:panelGroup>
                        </h:panelGroup>
                    </f:facet>
                    <h:outputText value="#{avail.recurring ? 'Yes' : 'No'}" />
                </h:column>

                <h:column>
                    <f:facet name="header">
                        <h:panelGroup layout="block" style="display: flex; align-items: center; justify-content: center;">
                            <h:outputText value="Total Slots" />
                            <h:panelGroup styleClass="sort-icons">
                                <h:commandLink action="#{availabilityController.sortByAsc('totalSlots')}" 
                                               rendered="#{!(availabilityController.sortColumn eq 'totalSlots' and availabilityController.sortAscending)}" 
                                               styleClass="sort-icon">▲</h:commandLink>
                                <h:commandLink action="#{availabilityController.sortByDesc('totalSlots')}" 
                                               rendered="#{!(availabilityController.sortColumn eq 'totalSlots' and not availabilityController.sortAscending)}" 
                                               styleClass="sort-icon">▼</h:commandLink>
                            </h:panelGroup>
                        </h:panelGroup>
                    </f:facet>
                    <h:outputText value="#{avail.totalSlots}" />
                </h:column>

                <h:column>
                    <f:facet name="header">
                        <h:panelGroup layout="block" style="display: flex; align-items: center; justify-content: center;">
                            <h:outputText value="Notes" />
                            <h:panelGroup styleClass="sort-icons">
                                <h:commandLink action="#{availabilityController.sortByAsc('notes')}" 
                                               rendered="#{!(availabilityController.sortColumn eq 'notes' and availabilityController.sortAscending)}" 
                                               styleClass="sort-icon">▲</h:commandLink>
                                <h:commandLink action="#{availabilityController.sortByDesc('notes')}" 
                                               rendered="#{!(availabilityController.sortColumn eq 'notes' and not availabilityController.sortAscending)}" 
                                               styleClass="sort-icon">▼</h:commandLink>
                            </h:panelGroup>
                        </h:panelGroup>
                    </f:facet>
                    <h:outputText value="#{avail.notes}" />
                </h:column>
                
                <h:column>
                    <f:facet name="header">
                        <h:outputText value="Update" />
                    </f:facet>
                    <h:commandButton value="Update" action="updateAvailability" styleClass="btn-update">
                        <f:setPropertyActionListener target="#{availabilityController.selectedAvailability}" value="#{avail}" />
                    </h:commandButton>
                </h:column>
            </h:dataTable>
        </div>

        <!-- Pagination -->
        <h:panelGroup layout="block" styleClass="pagination-panel">
            <h:commandButton value="First" action="#{availabilityController.goToFirstPage}"
                 disabled="#{availabilityController.currentPage == 1}" styleClass="btn btn-gray" />

            <h:commandButton value="Previous" action="#{availabilityController.previousPage}"
                             disabled="#{availabilityController.currentPage == 1}" styleClass="btn btn-gray" />
            
            <h:outputText value="Page #{availabilityController.currentPage} of #{availabilityController.totalPages}" />
            
            <h:commandButton value="Next" action="#{availabilityController.nextPage}"
                             disabled="#{availabilityController.currentPage == availabilityController.totalPages}" styleClass="btn btn-gray" />
            
            <h:commandButton value="Last" action="#{availabilityController.goToLastPage}"
                             disabled="#{availabilityController.currentPage == availabilityController.totalPages}" styleClass="btn btn-gray" />
        </h:panelGroup>
    </h:panelGroup>

    <h:outputText value="#{availabilityController.message}" styleClass="message" />
</h:form>
</f:view>
</body>
</html>
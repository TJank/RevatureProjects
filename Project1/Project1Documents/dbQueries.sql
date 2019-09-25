select UA.email, UA.employeeid, E.firstname, E.lastname, E.availableamount, PL.title

FROM 
    employee as E

    inner join 
    
    user_account as UA
        on
            E.id = UA.employeeid
    
    inner join

    employee_position as EP
        on 
            E.id = EP.employeeid

    inner join 
    
    position_level as PL
        on 
            PL.id = EP.position_level_id

WHERE
    UA.email = 'bobsaget@email.com'

Group by 
    Max(position_level_id)






select UA.email, UA.employeeid, E.firstname, E.lastname, E.availableamount, PL.title FROM employee as E inner join user_account as UA on E.id = UA.employeeid inner join employee_position as EP on E.id = EP.employeeid inner join position_level as PL on PL.id = EP.positionlevelid WHERE EP.positionlevelid = (select max(PL.id ) from position_level as PL inner join employee_position as EP on PL.id = EP.positionlevelid where EP.employeeid = 3)

select R.id, R.description, R.eventlocation, R.eventcost, R.justification, ET.eventtype, ET.gradeformat, R.timemissed, R.isurgent, R.eventstartdate, R.requesteddate, APS.approvalstatus, WS.waitingstatus from request as R inner join event_type as ET on R.eventtypeid = ET.id inner join approved_status as APS on APS.id = R.approvedstatusid inner join waiting_status as WS on WS.id = R.waitingstatusid where R.employeeid = 1;
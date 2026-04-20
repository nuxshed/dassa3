# IMS UML Design

## Classes

User, Student, Employee, Role, Permissions, Guardian, Batch, Course, Subject, Enrollment, Attendance Records, Leave, Student Leave, Employee Leave, LeaveApproval, News, Comments, Transaction, Fees, Donation, Expense, Payslip, Timetable, Slots, PersonalEvent, Exam, Question, GradingScheme, GradeCutoff, Result, Message, Notification, Admission Form, FormSubmission, Department, Dashboard

---

## Relationships

Notation: `▷` inheritance · `◆—` composition · `◇—` aggregation · `—` association.

### Inheritance
- Student `▷` User
- Employee `▷` User
- Guardian `▷` User
- Student Leave `▷` Leave
- Employee Leave `▷` Leave
- Fees `▷` Transaction
- Donation `▷` Transaction
- Expense `▷` Transaction
- Payslip `▷` Transaction

### User / Student / Employee
- User `*` — `1..*` Role   *(association — holdsRole)*
- Role `*` — `*` Permissions   *(association — grants)*
- User `1` — `*` Message   *(association — sender)*
- User `*` — `*` Message   *(association — receiver)*
- User `1` — `*` Comments   *(association — author)*
- User `1` — `*` News   *(association — author)*
- Student `1` — `1..*` Guardian   *(association)*
- Student `*` — `1` Batch   *(association — belongsTo)*
- Student `*` — `1` Course   *(association)*
- Student `1` — `*` Enrollment   `*` — `1` Subject   *(association — enrolls via Enrollment)*
- Student `1` — `*` Student Leave   *(association — applicant)*
- Student `1` — `*` Attendance Records   *(association)*
- Student `*` — `1` Timetable   *(association)*
- Student `1` — `*` PersonalEvent   *(association)*
- Employee `*` — `1` Department   *(association — belongsTo)*
- Employee `1` — `*` Employee Leave   *(association — applicant)*
- Employee `*` — `*` Batch   *(association — assignedTo)*
- Employee `*` — `*` Subject   *(association — teaches)*
- Payslip `*` — `1` Employee   *(association — paidTo)*
- Leave `1` `◆—` `1..2` LeaveApproval   *(composition — approvalChain)*
- LeaveApproval `*` — `1` Employee   *(association — approver)*

### Academic
- Course `1` `◆—` `*` Subject   *(composition)*
- Subject `1` — `*` Exam   *(association)*
- Subject `*` — `*` Slots   *(association — assignedTo)*
- Exam `*` — `1..*` Slots   *(association — assignedTo)*
- Exam `*` — `1` GradingScheme   *(association — follows)*
- Exam `1` `◆—` `*` Question   *(composition)*
- GradingScheme `1` `◆—` `*` GradeCutoff   *(composition)*
- Exam `1` `◆—` `*` Result   *(composition)*
- Result `*` — `1` Student   *(association — belongsTo)*
- Timetable `1` `◆—` `*` Slots   *(composition)*
- Attendance Records `*` — `1` Subject   *(association)*

### Other
- News `1` `◆—` `*` Comments   *(composition)*
- Student `1` — `*` Fees   *(association — payer)*
- Admission Form `1` — `*` FormSubmission   *(association — templateFor)*
- FormSubmission `1` — `0..1` Student   *(association — gives)*
- Dashboard `*` — `1` User   *(association — viewer)*
- Notification `*` — `0..*` User   *(association — singleTargets)*
- Notification `*` — `0..*` Batch   *(association — groupTargets)*

---

## Class Definitions

### User
| Attributes | Methods |
|---|---|
| id | login() |
| name | logout() |
| email | updateProfile() |
| passwordHash | changePassword() |
| phone | sendMessage(to, content) |
| profilePhoto | setLanguage(locale) |
| language | |
| timezone | |
| country | |
| isActive | |

### Student *(extends User)*
| Attributes | Methods |
|---|---|
| studentId | applyForLeave() |
| enrollmentDate | payFee(feeId, mode) |
| previousEducation | viewAttendance() |
| category | viewResults() |
| isAlumni | viewTimetable() |
| | transferBatch(toBatch, reason) |
| | addPersonalEvent(event) |

### Employee *(extends User)*
| Attributes | Methods |
|---|---|
| employeeId | applyForLeave() |
| joinDate | viewPayslip() |
| designation | approvePayslip(p) |
| | rejectPayslip(p, reason) |

### Role
| Attributes | Methods |
|---|---|
| roleId | assignPermission() |
| roleName | revokePermission() |

### Permissions
| Attributes |
|---|
| permissionId |
| name |
| module |
| action |

### Guardian *(extends User)*
| Attributes |
|---|
| guardianId |
| relation |
| isEmergencyContact |

### Batch
| Attributes |
|---|
| batchId |
| name |
| startDate |
| endDate |
| capacity |

### Course
| Attributes |
|---|
| courseId |
| name |
| description |
| duration |

### Subject
| Attributes |
|---|
| subjectId |
| name |
| code |
| credits |
| isElective |

### Enrollment
| Attributes |
|---|
| enrollmentId |
| grade |
| isElective |
| status |
| enrollmentDate |

### Attendance Records
| Attributes | Methods |
|---|---|
| recordId | markAttendance() |
| studentId | generateReport() |
| subjectId | |
| date | |
| status | |
| remarks | |

### Leave
| Attributes | Methods |
|---|---|
| leaveId | apply() |
| startDate | cancel() |
| endDate | |
| reason | |
| status | |
| stage | |

### Student Leave *(extends Leave)*
*No additional attributes*

### Employee Leave *(extends Leave)*
| Attributes |
|---|
| leaveType |

### LeaveApproval
| Attributes | Methods |
|---|---|
| approvalId | approve(remark) |
| leaveId | reject(remark) |
| approverEmployeeId | |
| level | |
| decision | |
| remark | |
| decidedAt | |

### News
| Attributes | Methods |
|---|---|
| newsId | publish() |
| title | edit() |
| content | delete() |
| createdAt | |
| isPublished | |

### Comments
| Attributes | Methods |
|---|---|
| commentId | delete() |
| content | |
| createdAt | |

### Transaction
| Attributes | Methods |
|---|---|
| transactionId | process() |
| amount | |
| date | |
| status | |
| mode | |

### Fees *(extends Transaction)*
| Attributes | Methods |
|---|---|
| dueDate | submit() |
| feeType | |
| isPaid | |

### Donation *(extends Transaction)*
| Attributes |
|---|
| donorName |
| purpose |

### Expense *(extends Transaction)*
| Attributes |
|---|
| category |
| description |

### Payslip *(extends Transaction)*
| Attributes | Methods |
|---|---|
| month | approve() |
| year | reject() |
| approvalStatus | |
| approvedByEmployeeId | |

### Timetable
| Attributes | Methods |
|---|---|
| timetableId | create() |
| createdAt | edit() |
| | delete() |
| | addSlot(slot) |
| | removeSlot(slotId) |
| | assignCourseToSlot(course, slot) |
| | detectConflict() |

### Slots
| Attributes |
|---|
| slotId |
| day |
| startTime |
| endTime |
| slotType *(lecture | tutorial | lab | exam)* |
| date *(nullable \u2014 set for exam slots)* |
| isRecurring |

### PersonalEvent
| Attributes |
|---|
| eventId |
| studentId |
| title |
| startAt |
| endAt |
| reminderAt |

### Exam
| Attributes | Methods |
|---|---|
| examId | create() |
| name | schedule() |
| type | addQuestion(q) |
| date | publishResults() |
| totalMarks | |
| applicabilityType | |
| applicabilityRef | |

### Question
| Attributes |
|---|
| questionId |
| examId |
| text |
| marks |
| correctAnswer |
| order |

### GradingScheme
| Attributes | Methods |
|---|---|
| schemeId | calculateGrade(score) |
| name | |
| type | |

### GradeCutoff
| Attributes |
|---|
| cutoffId |
| minScore |
| maxScore |
| grade |
| gradePoint |

### Result
| Attributes | Methods |
|---|---|
| resultId | generate() |
| marksObtained | |
| grade | |
| remarks | |

### Notification
| Attributes | Methods |
|---|---|
| notificationId | dispatch() |
| title | |
| content | |
| channel *(inApp | sms | email)* | |
| scope *(single | group)* | |
| createdAt | |

### Message
| Attributes | Methods |
|---|---|
| messageId | delete() |
| content | |
| sentAt | |
| isRead | |
| isBroadcast | |

### Admission Form
| Attributes | Methods |
|---|---|
| formId | addField(field) |
| formSchema *(JSON)* | publish() |
| createdByEmployeeId | |
| createdAt | |

### FormSubmission
| Attributes | Methods |
|---|---|
| submissionId | submit() |
| formId | review(decision, remark) |
| applicantName | approve() |
| formValues *(JSON)* | reject(reason) |
| submissionDate | |
| status | |
| reviewerEmployeeId | |

### Department
| Attributes |
|---|
| departmentId |
| name |
| description |

### Dashboard
| Attributes | Methods |
|---|---|
| dashboardId | search(query) |
| viewerUserId | navigateTo(entity) |
| | showLatestNews() |
| | getWidgets(role) |
| | render(user) |

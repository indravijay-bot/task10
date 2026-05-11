package sit707_tasks;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class TaskInboxTest {

    // Replace these with your real student details
    private static final String STUDENT_ID   = "224025533";
    private static final String STUDENT_NAME = "Indravijaysinh Zala";

    private TaskInbox inbox;

   
    @Before
    public void setUp() {
        inbox = new TaskInbox();
    }

    // -----------------------------------------------------------------------
    // Identity checks (required by the unit)
    // -----------------------------------------------------------------------

    @Test
    public void testStudentIdentity() {
    	String studentId = null;
        Assert.assertNotNull("Student ID is null", studentId);
        
    }

    @Test
    public void testStudentName() {
        Assert.assertNotNull("Student name must not be null", STUDENT_NAME);
        Assert.assertFalse("Student name must not be empty", STUDENT_NAME.isEmpty());
    }

    // -----------------------------------------------------------------------
    // TDD Test 1 – A student can submit a task successfully
    // -----------------------------------------------------------------------

    @Test
    public void testSubmitTask_validInput_returnsTrue() {
        boolean result = inbox.submitTask("s224025533", "Task 1P", "submitted", "");
        Assert.assertTrue("Submitting a valid task should return true", result);
    }

    // -----------------------------------------------------------------------
    // TDD Test 2 – Submitting with null/empty student ID should fail
    // -----------------------------------------------------------------------

    @Test
    public void testSubmitTask_nullStudentId_returnsFalse() {
        boolean result = inbox.submitTask(null, "Task 1P", "submitted", "");
        Assert.assertFalse("Null student ID should cause submission to fail", result);
    }

    @Test
    public void testSubmitTask_emptyStudentId_returnsFalse() {
        boolean result = inbox.submitTask("  ", "Task 1P", "submitted", "");
        Assert.assertFalse("Empty student ID should cause submission to fail", result);
    }

    // -----------------------------------------------------------------------
    // TDD Test 3 – Submitting with null/empty task title should fail
    // -----------------------------------------------------------------------

    @Test
    public void testSubmitTask_nullTaskTitle_returnsFalse() {
        boolean result = inbox.submitTask("s224025533", null, "submitted", "");
        Assert.assertFalse("Null task title should cause submission to fail", result);
    }

    @Test
    public void testSubmitTask_emptyTaskTitle_returnsFalse() {
        boolean result = inbox.submitTask("s224025533", "", "submitted", "");
        Assert.assertFalse("Empty task title should cause submission to fail", result);
    }

    // -----------------------------------------------------------------------
    // TDD Test 4 – Student can view their inbox (list of tasks)
    // -----------------------------------------------------------------------

    @Test
    public void testGetTasksForStudent_afterSubmit_returnsOneTask() {
        inbox.submitTask("s224025533", "Task 1P", "submitted", "");
        int count = inbox.getTaskCount("s224025533");
        Assert.assertEquals("Student should have exactly 1 task in inbox", 1, count);
    }

    @Test
    public void testGetTasksForStudent_differentStudents_isolated() {
        inbox.submitTask("s111111111", "Task 1P", "submitted", "");
        inbox.submitTask("s222222222", "Task 2P", "submitted", "");

        int countA = inbox.getTaskCount("s111111111");
        int countB = inbox.getTaskCount("s222222222");

        Assert.assertEquals("Student A should have 1 task", 1, countA);
        Assert.assertEquals("Student B should have 1 task", 1, countB);
    }

    @Test
    public void testGetTasksForStudent_unknownStudent_returnsEmptyList() {
        int count = inbox.getTaskCount("s999999999");
        Assert.assertEquals("Unknown student should have 0 tasks", 0, count);
    }

    // -----------------------------------------------------------------------
    // TDD Test 5 – hasTask checks whether a task exists in the inbox
    // -----------------------------------------------------------------------

    @Test
    public void testHasTask_existingTask_returnsTrue() {
        inbox.submitTask("s224025533", "Task 2P", "submitted", "");
        boolean found = inbox.hasTask("s224025533", "Task 2P");
        Assert.assertTrue("Task 2P should be found in the inbox", found);
    }

    @Test
    public void testHasTask_missingTask_returnsFalse() {
        boolean found = inbox.hasTask("s224025533", "Task 99P");
        Assert.assertFalse("Task 99P should not be in an empty inbox", found);
    }

    // -----------------------------------------------------------------------
    // TDD Test 6 – Task status is correct after submission
    // -----------------------------------------------------------------------

    @Test
    public void testGetTaskStatus_afterSubmit_isSubmitted() {
        inbox.submitTask("s224025533", "Task 3P", "submitted", "");
        String status = inbox.getTaskStatus("s224025533", "Task 3P");
        Assert.assertEquals("Status should be 'submitted' right after submission",
                "submitted", status);
    }

    @Test
    public void testGetTaskStatus_nonExistentTask_returnsNull() {
        String status = inbox.getTaskStatus("s224025533", "Task XYZ");
        Assert.assertNull("Status for non-existent task should be null", status);
    }

    // -----------------------------------------------------------------------
    // TDD Test 7 – Tutor can update feedback and status (simulated)
    // -----------------------------------------------------------------------

    @Test
    public void testUpdateTaskFeedback_validTask_statusChangesToComplete() {
        inbox.submitTask("s224025533", "Task 4P", "submitted", "");
        boolean updated = inbox.updateTaskFeedback(
                "s224025533", "Task 4P", "complete", "Great work! Well done.");
        Assert.assertTrue("Update should succeed for an existing task", updated);

        String newStatus = inbox.getTaskStatus("s224025533", "Task 4P");
        Assert.assertEquals("Status should now be 'complete'", "complete", newStatus);
    }

    @Test
    public void testUpdateTaskFeedback_nonExistentTask_returnsFalse() {
        boolean updated = inbox.updateTaskFeedback(
                "s224025533", "Ghost Task", "complete", "Some feedback");
        Assert.assertFalse("Update on non-existent task should return false", updated);
    }

    // -----------------------------------------------------------------------
    // TDD Test 8 – Student can read feedback from their task
    // -----------------------------------------------------------------------

    @Test
    public void testGetTaskFeedback_afterUpdate_returnsFeedback() {
        inbox.submitTask("s224025533", "Task 5P", "submitted", "");
        inbox.updateTaskFeedback("s224025533", "Task 5P",
                "resubmit", "Please add more test cases.");
        String feedback = inbox.getTaskFeedback("s224025533", "Task 5P");
        Assert.assertEquals("Feedback should match what the tutor set",
                "Please add more test cases.", feedback);
    }

    @Test
    public void testGetTaskFeedback_noFeedbackYet_returnsEmptyString() {
        inbox.submitTask("s224025533", "Task 6P", "submitted", "");
        String feedback = inbox.getTaskFeedback("s224025533", "Task 6P");
        Assert.assertEquals("Initial feedback should be empty", "", feedback);
    }

    // -----------------------------------------------------------------------
    // TDD Test 9 – Multiple tasks for same student
    // -----------------------------------------------------------------------

    @Test
    public void testGetTaskCount_multipleTasksSameStudent_correctCount() {
        inbox.submitTask("s224025533", "Task 1P", "submitted", "");
        inbox.submitTask("s224025533", "Task 2P", "submitted", "");
        inbox.submitTask("s224025533", "Task 3P", "submitted", "");

        int count = inbox.getTaskCount("s224025533");
        Assert.assertEquals("Student should have 3 tasks", 3, count);
    }

    // -----------------------------------------------------------------------
    // TDD Test 10 – Status resubmit flow
    // -----------------------------------------------------------------------

    @Test
    public void testUpdateTaskFeedback_resubmitFlow() {
        inbox.submitTask("s224025533", "Task 7P", "submitted", "");

        // Tutor marks as resubmit
        inbox.updateTaskFeedback("s224025533", "Task 7P",
                "resubmit", "Needs more coverage.");
        Assert.assertEquals("resubmit", inbox.getTaskStatus("s224025533", "Task 7P"));

        // Student fixes and resubmits (status goes back to submitted)
        inbox.updateTaskFeedback("s224025533", "Task 7P", "submitted", "");
        Assert.assertEquals("submitted", inbox.getTaskStatus("s224025533", "Task 7P"));

        // Tutor finally marks complete
        inbox.updateTaskFeedback("s224025533", "Task 7P",
                "complete", "All good now!");
        Assert.assertEquals("complete", inbox.getTaskStatus("s224025533", "Task 7P"));
    }
}

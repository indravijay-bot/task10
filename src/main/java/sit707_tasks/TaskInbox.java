package sit707_tasks;

import java.util.ArrayList;
import java.util.List;


public class TaskInbox {

  
    public static class Task {
        private String studentId;
        private String taskTitle;
        private String status;   // e.g. "submitted", "complete", "resubmit"
        private String feedback;

        public Task(String studentId, String taskTitle, String status, String feedback) {
            this.studentId = studentId;
            this.taskTitle = taskTitle;
            this.status    = status;
            this.feedback  = feedback;
        }

        public String getStudentId()  { return studentId; }
        public String getTaskTitle()  { return taskTitle; }
        public String getStatus()     { return status; }
        public String getFeedback()   { return feedback; }

        /**
         * Update the status of this task (e.g. after tutor review).
         */
        public void setStatus(String status) {
            this.status = status;
        }

        /**
         * Update the feedback for this task.
         */
        public void setFeedback(String feedback) {
            this.feedback = feedback;
        }
    }

    // Internal data store of all submitted tasks
    private List<Task> allTasks = new ArrayList<>();

    /**
     * Submit a new task to the inbox.
     *
     * @param studentId  the ID of the student submitting the task
     * @param taskTitle  the title/name of the task
     * @param status     initial status (should be "submitted")
     * @param feedback   initial feedback (usually empty on first submit)
     * @return true if submission is successful, false if studentId or title is null/empty
     */
    public boolean submitTask(String studentId, String taskTitle, String status, String feedback) {
        if (studentId == null || studentId.trim().isEmpty()) {
            return false;
        }
        if (taskTitle == null || taskTitle.trim().isEmpty()) {
            return false;
        }
        if (status == null || status.trim().isEmpty()) {
            return false;
        }
        Task task = new Task(studentId, taskTitle, status, feedback);
        allTasks.add(task);
        return true;
    }

    /**
     * Get all tasks belonging to a specific student.
     *
     * @param studentId the student's ID
     * @return list of tasks for that student; empty list if none found
     */
    public List<Task> getTasksForStudent(String studentId) {
        List<Task> result = new ArrayList<>();
        if (studentId == null || studentId.trim().isEmpty()) {
            return result;
        }
        for (Task t : allTasks) {
            if (t.getStudentId().equals(studentId)) {
                result.add(t);
            }
        }
        return result;
    }

    /**
     * Get the total number of tasks submitted by a student.
     *
     * @param studentId the student's ID
     * @return count of tasks
     */
    public int getTaskCount(String studentId) {
        return getTasksForStudent(studentId).size();
    }

    /**
     * Check whether a specific task (by title) exists in a student's inbox.
     *
     * @param studentId the student's ID
     * @param taskTitle the task title to look for
     * @return true if found, false otherwise
     */
    public boolean hasTask(String studentId, String taskTitle) {
        for (Task t : getTasksForStudent(studentId)) {
            if (t.getTaskTitle().equals(taskTitle)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the status of a specific task for a student.
     *
     * @param studentId the student's ID
     * @param taskTitle the task title
     * @return the task status string, or null if not found
     */
    public String getTaskStatus(String studentId, String taskTitle) {
        for (Task t : getTasksForStudent(studentId)) {
            if (t.getTaskTitle().equals(taskTitle)) {
                return t.getStatus();
            }
        }
        return null;
    }

    /**
     * Update the status and feedback of an existing task (simulates tutor review).
     *
     * @param studentId  the student's ID
     * @param taskTitle  the task title
     * @param newStatus  the new status to set
     * @param newFeedback the new feedback message
     * @return true if task was found and updated, false otherwise
     */
    public boolean updateTaskFeedback(String studentId, String taskTitle,
                                      String newStatus, String newFeedback) {
        for (Task t : getTasksForStudent(studentId)) {
            if (t.getTaskTitle().equals(taskTitle)) {
                t.setStatus(newStatus);
                t.setFeedback(newFeedback);
                return true;
            }
        }
        return false;
    }

    /**
     * Get the feedback message for a specific task.
     *
     * @param studentId the student's ID
     * @param taskTitle the task title
     * @return feedback string, or null if not found
     */
    public String getTaskFeedback(String studentId, String taskTitle) {
        for (Task t : getTasksForStudent(studentId)) {
            if (t.getTaskTitle().equals(taskTitle)) {
                return t.getFeedback();
            }
        }
        return null;
    }

    /**
     * Remove all tasks from the inbox (useful for resetting state in tests).
     */
    public void clearAllTasks() {
        allTasks.clear();
    }
}

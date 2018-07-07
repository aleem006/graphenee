package io.graphenee.jbpm.embedded;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.kie.api.task.TaskService;
import org.kie.api.task.model.Comment;
import org.kie.api.task.model.Status;
import org.kie.api.task.model.Task;
import org.kie.api.task.model.TaskSummary;
import org.kie.api.task.model.User;

import io.graphenee.jbpm.embedded.exception.GxAssignTaskException;
import io.graphenee.jbpm.embedded.exception.GxCompleteTaskException;
import io.graphenee.jbpm.embedded.exception.GxSkipTaskException;

public class GxUserTask implements TaskSummary {

	private TaskSummary task;
	WeakReference<TaskService> taskService;
	private Task internalTask;

	public GxUserTask(TaskService taskService, TaskSummary task) {
		this.taskService = new WeakReference<>(taskService);
		this.task = task;
	}

	@Override
	public Long getId() {
		return task.getId();
	}

	@Override
	public String getName() {
		return task.getName();
	}

	@Override
	public String getStatusId() {
		return task.getStatusId();
	}

	@Override
	public Integer getPriority() {
		return task.getPriority();
	}

	@Override
	public String getActualOwnerId() {
		return task.getActualOwnerId();
	}

	@Override
	public String getCreatedById() {
		return task.getCreatedById();
	}

	@Override
	public Date getCreatedOn() {
		return task.getCreatedOn();
	}

	@Override
	public Date getActivationTime() {
		return task.getActivationTime();
	}

	@Override
	public Date getExpirationTime() {
		return task.getExpirationTime();
	}

	@Override
	public String getProcessId() {
		return task.getProcessId();
	}

	@Override
	public Long getProcessInstanceId() {
		return task.getProcessInstanceId();
	}

	@Override
	public String getDeploymentId() {
		return task.getDeploymentId();
	}

	@Override
	public Long getParentId() {
		return task.getParentId();
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		task.writeExternal(out);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		task.readExternal(in);
	}

	@Override
	public String getSubject() {
		return getTask().getSubject();
	}

	@Override
	public String getDescription() {
		return task.getDescription();
	}

	@Override
	public Status getStatus() {
		return task.getStatus();
	}

	@Override
	public Boolean isSkipable() {
		return task.isSkipable();
	}

	@Override
	public User getActualOwner() {
		return task.getActualOwner();
	}

	@Override
	public User getCreatedBy() {
		return task.getCreatedBy();
	}

	@Override
	public Long getProcessSessionId() {
		return task.getProcessSessionId();
	}

	@Deprecated
	@Override
	public List<String> getPotentialOwners() {
		return task.getPotentialOwners();
	}

	@Override
	public Boolean isQuickTaskSummary() {
		return task.isQuickTaskSummary();
	}

	public List<Comment> getComments() {
		List<Comment> comments = getTaskService().getAllCommentsByTaskId(getId());
		comments = comments.stream().sorted((o1, o2) -> {
			if (o1.getAddedAt().after(o2.getAddedAt()))
				return -1;
			return 1;
		}).collect(Collectors.toList());
		return comments;
	}

	public Comment getLatestComment() {
		List<Comment> comments = getComments();
		if (comments != null && !comments.isEmpty())
			return comments.get(0);
		return null;
	}

	public String getComment() {
		Comment latestComment = getLatestComment();
		if (latestComment != null)
			return latestComment.getText();
		return null;
	}

	public String getCommentedBy() {
		Comment latestComment = getLatestComment();
		if (latestComment != null)
			return latestComment.getAddedBy().getId();
		return null;
	}

	protected TaskService getTaskService() {
		assert taskService.get() != null;
		return taskService.get();
	}

	public Task getTask() {
		if (internalTask == null)
			internalTask = getTaskService().getTaskById(getId());
		return internalTask;
	}

	public void complete(Map<String, Object> taskData) throws GxCompleteTaskException {
		try {
			getTaskService().complete(getId(), getActualOwnerId(), taskData);
		} catch (Exception ex) {
			throw new GxCompleteTaskException("Faild to complete task", ex);
		}
	}

	public void skip() throws GxSkipTaskException {
		try {
			getTaskService().skip(getId(), getActualOwnerId());
		} catch (Exception ex) {
			throw new GxSkipTaskException("Failed to skip task", ex);
		}
	}

	public void assign(String assignToUserId) throws GxAssignTaskException {
		try {
			getTaskService().delegate(getId(), getActualOwnerId(), assignToUserId);
		} catch (Exception ex) {
			throw new GxAssignTaskException("Failed to assign task", ex);
		}
	}

}

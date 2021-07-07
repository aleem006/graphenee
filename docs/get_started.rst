.. _Get Started:

Get Started
===========
	
 1. Start Eclipse
 2. Create Maven Projects
 3. Import Graphenee Dependecies(gx-vaadin, gx-vaadin-theme, vaadin-spring-boot-starter, vaadin-push). Link any database, we are using H2 databse for this project.
 4. Update Maven Project
 5. Run as Debug Mode
 6. A block of code has shown below which consists of java main class.
   	
   .. code-block:: bash
	
	package com.myapp.todoapp;

	import org.springframework.boot.SpringApplication;
	import org.springframework.boot.autoconfigure.SpringBootApplication;

 	@SpringBootApplication
	public class Application {

		public static void main(String[] args) {
			SpringApplication.run(Application.class, args);
		}

	}	

 7. Create a new class named ServletInitializer and extend it with SpringBootServletInitializer.
 8. After extending, override a configure method within the class which will return the spring application to the main application which is our main class “application.java”.

  .. code-block:: bash

	package com.myapp.todoapp;

	import org.springframework.boot.builder.SpringApplicationBuilder;
	import org.springframework.boot.web.support.SpringBootServletInitializer;

	public class ServletInitializer extends SpringBootServletInitializer {

		@Override
		protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
			// configure application for web container
			return builder.sources(Application.class);
		}

	}	

 9. Create another java class, extend it with abstract dashboard setup and add unimplemented methods, annotate it with Service and uiscope, view of application first page.

  .. code-block:: bash 

	package com.myapp.todoapp;

	import java.util.List;
	import com.vaadin.navigator.Navigator;
	import com.vaadin.ui.Image;
	import io.graphenee.core.exception.AuthenticationFailedException;
	import io.graphenee.core.exception.PasswordChangeRequiredException;
	import io.graphenee.vaadin.AbstractDashboardSetup;
	import io.graphenee.vaadin.TRMenuItem;
	import io.graphenee.vaadin.domain.DashboardUser;
	import io.graphenee.vaadin.event.DashboardEvent.UserLoginRequestedEvent;
	import com.vaadin.spring.annotation.UIScope;
	import org.springframework.stereotype.Service;

	@Service
	@UIScope
	public class DashboardSetup extends AbstractDashboardSetup {

	@Override
	public String applicationTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Image applicationLogo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<TRMenuItem> menuItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<TRMenuItem> profileMenuItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String dashboardViewName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerViewProviders(Navigator navigator) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DashboardUser authenticate(UserLoginRequestedEvent event) throws AuthenticationFailedException, PasswordChangeRequiredException {
		// TODO Auto-generated method stub
		return null;
	}

	}


 10. Create MainUI.java extend it with AdstractDashboardUI, add unimplemented method and autowire SpringViewProvider and DashboardSetup class here.
 11. Annotate this class with SpringUI, Theme, Push and Viewport.

  .. code-block:: bash 
 	
	package com.myapp.todoapp;

	import org.springframework.beans.factory.annotation.Autowired;
	import com.vaadin.annotations.Push;
	import com.vaadin.annotations.Theme;
	import com.vaadin.annotations.Viewport;
	import com.vaadin.shared.communication.PushMode;
	import com.vaadin.shared.ui.ui.Transport;
	import com.vaadin.spring.annotation.SpringUI;
	import com.vaadin.spring.navigator.SpringViewProvider;
	import io.graphenee.vaadin.AbstractDashboardSetup;
	import io.graphenee.vaadin.AbstractDashboardUI;
	import com.myapp.todoapp.DashboardSetup;

	@SpringUI
	@Theme("graphenee")
	@Push(transport = Transport.WEBSOCKET, value = PushMode.MANUAL)
	@Viewport(value = "width = device-width")
	public class MainUI extends AbstractDashboardUI {

	@Autowired
	SpringViewProvider viewProvider;

	@Autowired
	DashboardSetup dashboardSetup;

	@Override
	protected AbstractDashboardSetup dashboardSetup() {
		// return a view
		return dashboardSetup;
	}

	}
 
 12. Create a class for view, extend it with AbstractDashboardPanel and implements MView, add unimplemented methods,  must annotate it with SpringView , add this in DashboardSetup.java.

  .. code-block:: bash
	
	package com.myapp.todoapp;

	import org.vaadin.viritin.navigator.MView;
	import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
	import io.graphenee.vaadin.AbstractDashboardPanel;
	import org.springframework.context.annotation.Scope;
	import com.vaadin.spring.annotation.SpringView;

	
	@SpringView(name = DefaultDashboardView.VIEW_NAME)
	@Scope("prototype")
	public class DefaultDashboardView extends AbstractDashboardPanel implements MView{
	
	public static final String VIEW_NAME = "todo-list";

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean beforeViewChange(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void afterViewChange(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String panelTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void postInitialize() {
		// TODO Auto-generated method stub
		
	}

	}		

 13. DashboardViewName() return default view.java in DashboardSetup.java

  .. code-block:: bash
	
	@Override
	public String dashboardViewName() {
		// Add default view name here
	return DefaultDashboardView.VIEW_NAME;
	}

 14. Create a database connection, graphenee need a database and save it with the name as “application.properties” inside the resources packages. 

  .. code-block:: bash

	flyway.enabled=false
	spring.datasource.url=jdbc:h2:mem:tododb  //databasename
	spring.datasource.username=demo 
	spring.datasource.password=password
	spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

 13. Login/Authentication Page
 
 .. image:: images/loginPage.png
	:width: 800
	:alt: Alternative text

 14.First page After Login

 .. image:: images/firstView.png
	:width: 800
	:alt: Alternative text   
 
 14. Create a new java class, extend it with abstract entity list panel , create constructor, add unimplemented methods and annotate it with spring component and scope.

 .. code-block:: bash
	
	package com.myapp.todoapp;

	import java.util.ArrayList;
	import java.util.List;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.context.annotation.Scope;
	import com.vaadin.spring.annotation.SpringComponent;
	import io.graphenee.vaadin.AbstractEntityListPanel;
	import io.graphenee.vaadin.TRAbstractForm;

	@SpringComponent
	@Scope("prototype")
	public class TodoPanel extends AbstractEntityListPanel<TodoListBean> {

	@Autowired
	TodoForm todoForm;

	List<TodoListBean> list = new ArrayList<>();

	public TodoPanel() {
		super(TodoListBean.class);
	}

	@Override
	protected boolean onSaveEntity(TodoListBean entity) {
		//on save button action
		return false;
	}

	@Override
	protected boolean onDeleteEntity(TodoListBean entity) {
		// on delete button action
		return false;
	}

	@Override
	protected String panelCaption() {
		// Provide Panel Name here in string
		return null;
	}

	@Override
	protected List<TodoListBean> fetchEntities() {
		return list;
	}

	@Override
	protected String[] visibleProperties() {
		//provide the name you want to show in panel from you data
		return null;
	}

	@Override
	protected TRAbstractForm<TodoListBean> editorForm() {
		//return form here
		return null;
	}
	}

 15. Autowire panel class here in DefaultDashboardView.java, refresh this panel and add panel componnet in view. 

 .. code-block:: bash
	 
	package com.myapp.todoapp;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.context.annotation.Scope;
	import org.vaadin.viritin.navigator.MView;
	import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
	import com.vaadin.spring.annotation.SpringView;
	import io.graphenee.vaadin.AbstractDashboardPanel;

	@SpringView(name = DefaultDashboardView.VIEW_NAME)
	@Scope("prototype")
	public class DefaultDashboardView extends AbstractDashboardPanel implements MView {

	public static final String VIEW_NAME = "todo-list";

	@Autowired
	TodoPanel todoPanel;

	@Override
	public void enter(ViewChangeEvent event) {
		//always call before view build
		todoPanel.refresh();
	}

	@Override
	public boolean beforeViewChange(ViewChangeEvent event) {
		return true;
	}

	@Override
	public void afterViewChange(ViewChangeEvent event) {
	}

	@Override
	protected String panelTitle() {
		// Panel Name Optional
		return "ToDo Panel";
	}

	@Override
	protected void postInitialize() {
		// build component 
		addComponent(todoPanel.build());
	}

	@Override
	protected boolean shouldShowHeader() {
		return true;
	}

	}

 16. View with panel, Panel have default Buttons 

 .. image:: images/panel.png
	:width: 800
	:alt: alternate text

 17. Create a new java class (bean), which have getter setter

 .. code-block: bash

 	package com.myapp.todoapp;

	public class TodoListBean {

	private String title;
	private String description;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	}
	
 18. Create a new class, for form, extend it with TRAbstractForm and provide type of Bean which is created.

 .. code-block: bash

	package com.myapp.todoapp;

	import org.springframework.context.annotation.Scope;
	import org.vaadin.viritin.fields.MTextField;
	import com.vaadin.data.fieldgroup.PropertyId;
	import com.vaadin.spring.annotation.SpringComponent;
	import com.vaadin.ui.FormLayout;
	import io.graphenee.vaadin.TRAbstractForm;

	@SuppressWarnings("serial")
	@SpringComponent
	@Scope("prototype")
	public class TodoForm extends TRAbstractForm<TodoListBean> {

	@PropertyId("title")
	MTextField titleTextField;

	@PropertyId("description")
	MTextField descriptionTextField;

	@Override
	protected void addFieldsToForm(FormLayout form) {
		titleTextField = new MTextField("Title").withRequired(true);
		descriptionTextField = new MTextField("Description").withRequired(true);
		form.addComponents(titleTextField, descriptionTextField);
	}

	@Override
	protected boolean eagerValidationEnabled() {
		return false;
	}

	@Override
	protected String formTitle() {
		return "Todo List";
	}

	@Override
	protected String popupWidth() {
		return "400px";
	}

	@Override
	protected String popupHeight() {
		return "200px";
	}

	}	

 19. Form 

 .. image:: images/form.png
	:width: 800
	:alt: alternate text

 20. Add CRUD operations in TodoPanel.java

 .. code-blocl: bash

	package com.myapp.todoapp;

	import java.util.ArrayList;
	import java.util.List;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.context.annotation.Scope;
	import com.vaadin.spring.annotation.SpringComponent;
	import io.graphenee.vaadin.AbstractEntityListPanel;
	import io.graphenee.vaadin.TRAbstractForm;

	@SpringComponent
	@Scope("prototype")
	public class TodoPanel extends AbstractEntityListPanel<TodoListBean> {

	@Autowired
	TodoForm todoForm;

	List<TodoListBean> list = new ArrayList<>();

	public TodoPanel() {
		super(TodoListBean.class);
	}

	@Override
	protected boolean onSaveEntity(TodoListBean entity) {
		//on save button action
		TodoListBean bean = new TodoListBean();
		bean.setTitle(entity.getTitle());
		bean.setDescription(entity.getDescription());
		list.add(bean);
		return true;
	}

	@Override
	protected boolean onDeleteEntity(TodoListBean entity) {
		// on delete button action
		list.remove(entity);
		return true;
	}

	@Override
	protected String panelCaption() {
		// Provide Panel Name here in string
		return null;
	}

	@Override
	protected List<TodoListBean> fetchEntities() {
		return list;
	}

	@Override
	protected String[] visibleProperties() {
		//provide the name you want to show in panel from you data
		return new String[] { "title", "description" };
	}

	@Override
	protected TRAbstractForm<TodoListBean> editorForm() {
		//return form here
		return todoForm;
	}
	}

 19. Our TOdo application

 .. image:: images/app.png
	:width: 800 
	:alt: alternate 

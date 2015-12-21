This page describes how you can install the Next Data Viewer for your application to see data locally as well as on  Google App Engine

Runnign demo of this application can be found at http://gaedataviewer.appspot.com/


= if you ahve any issue feel free to post here http://code.google.com/p/gae-data-viewer/issues

# Introduction #
Using this simple tool you can quickly add CRUD access to your JDO object with in seconds.
Try it your self its very simple. I hope this will help GAE communtiy.
Currently i am working on Import/Export feature. Will be available shortly.


# Details #
1) Download "GWTCommon.jar"and "NextDataViewer.jar" from http://code.google.com/p/gae-data-viewer/downloads/list and put them in lib folder of your GWT project.

**Client Side setup**

2) Create a html where you want to see the  NextDataViewer. Make sure you add following line in your html
```
     <div id="NextDataViewerContent" ></div>
```
make sure you are including your project's .js file in this html.

3) Inherit the GWT project
Put following line in you GWT project's .gwt.xml file
```
<inherits name='com.next.viewer.NextDataViewer'/>
```

**Server Side setup**

3) Create a controller extending the class, com.next.viewer.server.DBServiceAbstract.

> Implement the abtract method
> public Object getPersistanceManagerFactory().

If you follow the google's example to create the PersistanceManagerFactory for JDO object then implentaion of this class/function will be something like this
```
package com.mypackage.server;

Class MyControllerClass extends DBServiceAbstract{
	@Override
	public Object getPersistanceManagerFactory() {
		return PMF.get();
	}
}
```
4) Update your web.xml. Add the following code
```
  <servlet>
    <servlet-name>DBServlet</servlet-name>
    <servlet-class>the class which you just created(com.mypackage.server.MyControllerClass)</servlet-class>
  </servlet>
    <servlet-mapping>
        <servlet-name>DBServlet</servlet-name>
        <url-pattern>/<your application context>/dataviewer</url-pattern>
    </servlet-mapping>
```


After all this setup you are ready to deploy your application

once deployed access your application locally or at google app engine

http://localhost:8080/<your application>/<html you created in step 2, you may need to adjust the path depedning on your project struture>

http://<your application>.appspot.com/<html you created in step 2, you may need to adjust the path depedning on your project struture>

this url will display something like this
[http://gae-data-viewer.googlecode.com/files/first.JPG](http://code.google.com/p/gae-data-viewer)

Here go to Admin and click on entity , it will display like this
[http://gae-data-viewer.googlecode.com/files/EntityDef.JPG](http://code.google.com/p/gae-data-viewer)


Then click on create

[http://gae-data-viewer.googlecode.com/files/EntityDefinitionEmpty.JPG](http://code.google.com/p/gae-data-viewer)

Enter Entity class name as your project's entity class which you deployed with this project e.g. com.next.data.viewer.server.entity.Customer

[http://gae-data-viewer.googlecode.com/files/EntityDefinitionFilled.JPG](http://code.google.com/p/gae-data-viewer)


once entity name entered it will populate the primary key field combo box, choose your entity's primary key and click save.
To close the box click cancel after saving.

Then you can search your entity, just hit the search button and it will show you your entity you just created.

[http://gae-data-viewer.googlecode.com/files/EntitySearch.JPG](http://code.google.com/p/gae-data-viewer)


Now refresh your page by pressing F5 or clicking refresh button on your browser(working on this, so you dont need to refresh, please bear with me for some time). Now your entities will come under Entities menu

[http://gae-data-viewer.googlecode.com/files/AllEntities.JPG](http://code.google.com/p/gae-data-viewer)

Click on any of Entity and you will be able to do Insert/Update/Delete/Search operation on it.
netbeans-samples
================
Netbeans Platform samples

Here you'll find fully working Netbeans Platform based application demos on how to accomplish different pretty standard application features.

## nodes/node-samples
Shows a BeanTreeView backed by a SQL database through a loosely coupled DAO implementation. 
The BeanTreeView shows a tree structure of Categories (nodes).

The Nodes have the following capabilities added to them
* Create new child category
* Rename category
* Delete/remove category

Through WeakListeners registered on the entity classes the UI is kept current.

### SQL Setup
The configuration is setup to use the following properties
Hostname: localhost
DB-Port: 3306 (MySQL) or 5432 (PostgreSQL)
Database: netbeans-samples
Username: netbeans-samples
Password: secretpassword123

This can be changed in net.flinkgutt.samples.nodes.domain.dao.SuperDAO.java ("domain-dao" module) if so desired.

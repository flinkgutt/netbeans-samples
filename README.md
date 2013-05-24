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

### SQL Setup (db-manager module)
There is an Database Server Manager available in the application where you can define your server settings.
It's limited to MySQL and PostgreSQL for now.
There is an option for tunneling, but it's not implemented quite yet.
On connect it sets up the small sample table needed for the Nodes, it will do this every time you connect.


### NbPreferences
The SQL Server settings are stored using NbPreferences, so it might be something to take a gander at. 
Located in the db-manager module.

### JdbcTemplate (Database communication)
DAOs and mapper located in the 'domain-dao' module.
Shows one way of putting this all together, some junk code as of now.
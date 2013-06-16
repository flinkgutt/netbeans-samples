netbeans-samples
================
Netbeans Platform samples

Here you'll find fully working Netbeans Platform based application demos on how to accomplish different pretty standard application features.
It's nowhere near complete or ready to be used as an example for most things, but it's a start for a discussion and hopefully some community participation. :-)

## nodes/node-samples
Shows a BeanTreeView backed by a SQL database through a loosely coupled DAO implementation. 
The BeanTreeView shows a tree structure of Categories (nodes).

The Nodes have the following capabilities added to them
* Create new child category
* Rename category
* Delete/remove category

Through WeakListeners registered on the entity classes the UI is kept current.

### Undo/Redo + Savable
ProductEditor now supports undo/redo for product name and description (in the "editor" part, not the table).
It's just a very simple example and could use some usability improvements. Warning when changing into a fresh state from a dirty one etc.

### SQL Setup (db-manager module)
There is an Database Server Manager available in the application where you can define your server settings.
It's limited to MySQL and PostgreSQL for now.
There is an option for tunneling, but it's not implemented quite yet.
On connect it sets up the small sample table needed for the Nodes, it will do this every time you connect.
It's recommended to use the username "netbeans-samples", if not you should probably take a look at mysql.sql and/or postgresql.sql

### NbPreferences
The SQL Server settings are stored using NbPreferences, so it might be something to take a gander at. 
Located in the db-manager module.

### JdbcTemplate (Database communication)
DAOs and mapper located in the 'domain-dao' module.
Shows one way of putting this all together, some junk code as of now.
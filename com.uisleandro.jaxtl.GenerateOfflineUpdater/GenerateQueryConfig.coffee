GenerateQuery = require './GenerateQuery'
InsertPlugin = require './SqliteInsert'

# The ideal scenario is when the class is auto configured
# so for this i would need an observer pattern
# add all instances as observers
# and wait for something happen
# like the new instance of the objects
# i must have some kind of interface to make it up
# generateQuery = new GenerateQuery()
# generateQuery.injectInsert InsertQuery


# Now this class needs to know how to register itself
# The method has a known name
generateQuery = new GenerateQuery()
generateQuery.registerPlugin new InsertPlugin()

generateQuery.run()

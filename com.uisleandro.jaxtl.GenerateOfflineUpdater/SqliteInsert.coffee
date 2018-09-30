GenerateQuery = require './GenerateQuery'

class InsertPlugin
  configure:(obj)->
    obj.injectInsert (cl1)->
      queryString = "INSERT INTO #{cl1.name} "
      chi = cl1.children
      if chi
        comma = "";
        chi.forEach (e)->
          if ! e.isFk
            queryString += "#{comma}#{e.name}"
          else
            queryString += "#{comma}fk_#{e.name}"
          comma = ", "
        queryString += " VALUES "
        comma = "";
        chi.forEach (e)->
          if ! e.isFk
            queryString += "#{comma}?"
          else
            queryString += "#{comma}?#{e.name}_id"
          comma = ", "
      queryString += ";\n# SELECT #{cl1.name}_id FROM #{cl1.name} ORDER BY #{cl1.name}_id DESC;\n\n"
      return queryString


module.exports = InsertPlugin

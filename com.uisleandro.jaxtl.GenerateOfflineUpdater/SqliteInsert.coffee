###
  insert_query
  exists_query
  delete_query
  update_query
  select_query

  I have to think in terms of packages:
  IInsert, ISelect, IUpdate, IDelete
  IContentProvider

###

module.exports = DatabaseInsert: (cl1)->
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

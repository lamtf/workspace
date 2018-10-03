GenerateQuery = require './GenerateQuery'

###
  o que eu vou gerar?

    O banco de dados
    Varias Interfaces com o Banco de dados
      -> Basic CRUD  (IInsert, IUpdate, IDelete, ISelectOne, ISelectList, ISelectValue* IExists)
      -> IQuery  (Mais complicado, fazer primeiro: Insert e Update e Select)
      -> ICustom
    O codigo de atualizacao do banco de dados
    O Content Provider
    O codigo de Acesso Direto
    O codigo de acesso via Content Provider
    TODO: as consultas tem paramatro xxxIn e xxxOut
###


class InsertPlugin
  configure:(obj)->
    obj.injectDoSomeWork (opName, viewName, sortedTables, stereotypes)->
      queryString = "#{viewName.ToCamelCase()} "
      i = 0;
      if stereotypes and (stereotypes.find (s)-> s.tagName == 'query:Insert')
        sortedTables.forEach (cl1)->
          queryString += "string query#{(i++)}=\""
          queryString += "INSERT INTO #{cl1.name} "
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
          queryString += "\"";
          queryString += ";\n# SELECT #{cl1.name}_id FROM #{cl1.name} ORDER BY #{cl1.name}_id DESC;\n\n"
      console.log queryString
      return

module.exports = InsertPlugin

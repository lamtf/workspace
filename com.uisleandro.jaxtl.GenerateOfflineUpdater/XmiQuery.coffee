{ condition, select, testall, query, queryR, like, contains_text_like, contains_attribute_equals, attribute_name_is_like, is_named, contains_tagName } = require './XmlJsQuery.coffee'

class XmiQuery
  constructor:()->
    @ALL_STEREOTYPES = null
  getAllStereotypes:(a)->
    queryR select(a).where attribute_name_is_like("base_")

  getAllByType:(umlElement, umlType)->
    queryR select(umlElement).where contains_attribute_equals("xmi:type",umlType)

  getStereotypeById:(a)->
    query select(ALL_STEREOTYPES).where contains_attribute_equals("xmi:id",a)

  _getAllClasses=(jPackage)->
    queryR select(jPackage).where contains_attribute_equals("xmi:type","uml:Class")

  getAllClasses:(jPackage)->
    if(jPackage.length > 0)
      clss = []
      jPackage.forEach (cl)->
        _getAllClasses cl
        .forEach (c)->
          clss.push c
      return clss
    else
      return _getAllClasses jPackage

  getAllOperations:(umlElement, umlType)->
    queryR select(umlElement).where contains_attribute_equals("xmi:type","uml:Operation")

  getAllParameters:(umlElement, umlType)->
    queryR select(umlElement).where contains_attribute_equals("xmi:type","uml:Parameter")

  getAllNamedClasses:(jPackage)->
    isNamedClass=(a)->
      contains_attribute_equals("xmi:type","uml:Class")(a) and is_named()(a)
    queryR select(jPackage).where isNamedClass

  #usar meramente para imprimir
  getForeignKeys:(jClassList, jClass)->
    isForeignKey=(a)->
      i = 0
      if contains_attribute_equals("xmi:type","uml:Property")(a)
        while(i < jClassList.length)
          return true if a.getAttr("name") is jClassList[i].getAttr("name")
          i++
      return false
    queryR select(jClass).where isForeignKey


  getUmlElementById:(jsonData,a)->
    query select(jsonData).where contains_attribute_equals("xmi:id",a)

  getPackageByName:(jPackage, name)->
    is_mvc = (a)->
      contains_attribute_equals("xmi:type","uml:Package")(a) and contains_attribute_equals("name",name)(a)
    return queryR select(jPackage).where is_mvc

module.exports = XmiQuery

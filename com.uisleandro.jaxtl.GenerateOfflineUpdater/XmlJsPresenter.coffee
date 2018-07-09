class XmlJsPresenter
  constructor:(obj, tabs)->
    value = ""
    if not tabs?
      tabs = ""
    if obj?
      value += "<"+obj.tagName

      if obj.properties?
        i = 0
        while(i < obj.properties.length)
          value += " #{obj.properties[i].name}=\"#{obj.properties[i].value}\""
          i++

      value += ">"

      if obj.innerText?
        value += obj.innerText

      if obj.children?
        i = 0
        while(i < obj.children.length)
          value += disp obj.children[i],tabs + "  "
          i++

      value += "</"+obj.tagName+">"

module.exports = XmlJsPresenter

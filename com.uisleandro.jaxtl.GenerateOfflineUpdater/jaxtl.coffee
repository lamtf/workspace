###
	JAXTL:     Javascript And XML Transformation Library
	Author:    Uisleandro Costa dos Santos
  Email:     uisleandro@gmail.com
	License:   https://creativecommons.org/licenses/by/4.0/
###


uml_model = "../com.uisleandro.localdiagram/__out2017/behavior_model_v4.uml"
target_file = "../com.uisleandro.generated/com.uisleandro.Store.Core/src/main/java/com/uisleandro/store/OfflineDatabaseUptader.java"

###
Agora eu preciso fazer um parsing
que me de condição de gerar codigo
de validação a partir da estrutura

###


###
Eu acho bom ter um id unico para cada objeto
uis:id="1"
Com um id unico eu posso fazer referencias (circulares)
a outros objetos dentro do mesmo modelo

###

###
	usar um xsd para validar o xml
	usar o xml pra validar o uml.xmi
###

class XmlParser
	### line = 0 ###
	class Stack
		lastid = 0|0
		constructor: ()->
			@data = []
			lastid = 0
		push:(obj) ->
			@data[@data.length] = create_node obj,lastid,if @data.length is 0 then 0 else @data[@data.length - 1]
			lastid++
			return @data[@data.length-1]
		peek:()->
			@data[@data.length-1]
		popcheck:(x)->
			### check_node_name @data[@data.length-1], x ###
			if @data[@data.length-1].tagName is x
				return @pop()
			else
				throw "error: on #{x} != #{@data[@data.length-1].tagName}"
				### throw "error: on #{line}: #{x} != #{@data[@data.length-1].tagName}" ###
		check:(x)->
			@data[@data.length-1] == x
		pop:()->
			x = @data[@data.length-1]
			@data.length = @data.length - 1
			if @data.length > 0
				add_children @data[@data.length-1],x
			else
				x
		log:(x)->
			if x?
				if @data[@data.length-1] == x
					console.log "Passed"
				else
					console.log "Failed"
			else
				console.log @data[@data.length-1]

	isOpeningTag = (x,i) ->
		x[i] == '<' and ((x[i+1] >= 'a' and x[i+1] <= 'z') or (x[i+1] >= 'A' and x[i+1] <= 'Z'))
	isClosingTag = (x,i) ->
		x[i] == '<' and x[i+1] == '/'
	isClosedTag = (x,i) ->
		x[i] == '/' and x[i+1] == '>'

	isOpenXmldef = (x,i) ->
		x[i] is '<' and x[i+1] is '?'

	isCloseXmldef = (x,i) ->
		x[i] is '?' and x[i+1] is '>'

	isSpace = (x) ->
		x is ' ' or x is '\r' or x is '\n' or x is '\t'

	getNextProperty = (x,i) ->
		n = ""
		v = ""
		### novo ###
		if not isSpace x[i]
			return null
		while(isSpace x[i])
			i++
		if x[i] is '/' or x[i] is '>'
			return null
		if (x[i] >= 'a' and x[i] <= 'z') or (x[i] >= 'A' and x[i] <= 'Z')
			while(x[i] isnt ' ' and x[i] isnt '=' and x[i] isnt '/' and x[i] isnt '>')
				n += x[i]
				i++
		while(isSpace x[i])
			i++
		if x[i] is '='
			i++
			while(isSpace x[i])
				i++
			if x[i] is '"'
				i++
				while(x[i] isnt '"')
					v += x[i]
					i++
				i++
		name : n
		value : v
		index : i

	getTagName = (x,i) ->
		tagName = ""
		while(x[i] isnt '>' and x[i] isnt '/' and not isSpace x[i])
			tagName += x[i]
			i++
		tagName : tagName
		index  : i

	###
	check_node_name = (node,nodeName)->
		node.tagName is nodeName
	###

	create_node = (nodeName, id, parentNode)->
		tagName : nodeName
		tagId: id
		getParent : ()->
			parentNode
		getAttr:(a)->
			i = 0
			while i < @.properties.length
				if @.properties[i].name is a
					return @.properties[i].value
				i++


	add_children = (parent, child)->
		if parent?
			if not parent.children?
				parent.children = []
			parent.children[parent.children.length] = child
			return parent
		else
			child

	add_property = (element, propertyName, propertyValue)->
		if not element.properties?
			element.properties = []
		element.properties[element.properties.length] =
			name: propertyName
			value : propertyValue
		element

	###
	get_xml_def=(x,i)->
		_name = ""
		_value = ""
		res = []
		while (x[i] isnt '>' and not (x[i] is '?' and x[i+1] is '>' and isSpace x[i]))
			i++
			while(x[i] isnt '=')
				_name += x[i]

			if x[i] is '='
				i++
				while x[i] isnt '"'
					i++
				while(x[i] isnt '""')
					_value += x[i]
					i++

			if _name isnt "" and _value isnt ""
				res[res.length] = {
					name : _name
					value : _value
				}
		{
			index : i
			properties : res
		}
	###

	parse : (x)->
		stack = new Stack()
		result = null
		i = 0
		while(i < x.length)

			if isOpenXmldef x,i
				while(!isCloseXmldef x,i)
					i++
				i+=2
			else if isOpeningTag x,i
				i++
				t = getTagName x,i
				stack.push t.tagName
				i = t.index
				### What is previously on the top be the parent of this new node ###

				prop = getNextProperty x,i

				while(null isnt prop)
					### If i found a property id be inserting it here ###
					add_property stack.peek(),prop.name,prop.value
					### stack.peek()[prop.name]=prop.value ###
					i = prop.index
					prop = getNextProperty x,i

				while(x[i] == ' ')
					i++

				### nao conseguiu fechar o BR ###

				if isClosedTag x,i
					### Add the actual node to the parent ###
					result = stack.pop()
					i += 2

			else if isClosingTag x,i
				t = getTagName x,i+2
				i = t.index
				result = stack.popcheck t.tagName
				### Add the actual node to the parent ###
			else
				while(x[i] is '/' or x[i] is '>' or isSpace x[i])
					i++
				if x[i]? and x[i] isnt '<'
					text = ""
					while(x[i] isnt '<')
						text += x[i]
						i++
						stack.peek()["innerText"]=text
						### add_property stack.peek(),"innerText",text ###
		return result

### eu preciso ter properties like props = [{name: value}] ###

disp = (obj, tabs)->
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


###

xsd:element
	Its used to make a xmltag

xsd:attribute
	Its used to make a single attribute in line

	by reference : look in to another place for definition
	or
	by name

	"name": nome do atributo
	"type": tipo do atributo
	"use"="optional" --> se o atributo não for obrigatorio

xsd:attributeGroup
	Its used to make a set of attributes
		by reference  : look in to another place for definition
		or
		by name

xsd:choice
	escolher um dos elementos internos, acho

xsd:any
	não sei o que significa. acho que significa qualquer tag
	acho que significa que os conteudos do no não são processados por este xsd

xsd:anyAttribute
	o mesmo do anterior..


xsd:complexType
	definição de um tipo: pode ser um tipo de um nó ou o tipo de um atributo

xsd:complexContent
	descrição do "parent complexType node"

xsd:extension
	contem o atributo obrigatorio "base" que aponta para o "parent node"

dos atributos das tags:
	maxOccurs=0 -> menor numero de ocorrencias, pode ser qualquer numero, ou unbounded
	maxOccurs="unbounded" -> maior numero de ocorrencias, pode ser qualquer numero, ou unbounded
	processContents="skip" -> os conteudos do nodo não devem ser processados por este xsd



###


# better use logic opeations and make a single chain of conditions
class condition
	constructor: (@a)->
		@ws = [[]]
		@w = @ws[0]
	where: (f)->
		@w[@w.length] = f
		@
	and: (f)->
		@where(f)
	or: (f)->
		@ws[@ws.length] = []
		@w = @ws[@ws.length-1]
		@where(f)

select = (a)->
	new condition(a)

### rever ###
testall = (fn,v)->
	i = 0
	while(i < fn.length)
		if(!fn[i](v))
			return false
		i++
	return true

query=(x)->
	r = []
	i = 0
	j = 0
	while(j < x.ws.length)
		while(i < x.a.length)
			if testall x.ws[j], x.a[i]
				r[r.length] = x.a[i]
			i++
		j++
	r
#@queryR
queryR=(x, r, root)->
	if "undefined" is typeof r
		r = []
		root = x.a
	j = 0
	while(j < x.ws.length)
		if testall x.ws[j], root
			r[r.length] = root
			j = j + 9999
		j++
	if root.children
		i = 0
		while(i < root.children.length)
			r = queryR(x, r, root.children[i])
			i++
	return r
###
equals=(a,b)->
	a is b
###

like=(a,b)->
	a.toUpperCase().indexOf(b.toUpperCase()) > -1;

contains_text_like=(text)->
	(a)->
		if typeof a.innerText is "undefined"
			return false
		else
			return a.innerText.indexOf(text) > -1

### i cant use prototype into a object ###
contains_attribute_equals=(k,v)->
	(a)->
		if typeof a.getAttribute isnt "function"
			if typeof a.properties isnt "undefined"
				i = 0
				while(i < a.properties.length)
					if(a.properties[i].name is k and a.properties[i].value is v)
						return true
					i++
			return false
		else
			return a.getAttribute(k) is v

attribute_name_is_like=(k)->
	(a)->
		if typeof a.getAttribute isnt "function"
			if typeof a.properties isnt "undefined"
				i = 0
				while(i < a.properties.length)
					if(like(a.properties[i].name,k))
						return true
					i++
			return false
		else
			return a.getAttribute(k) is v


is_named=(k)->
	(a)->
		if typeof a.properties isnt "undefined"
			i = 0
			while i < a.properties.length
				if a.properties[i].name is 'name'
					return true
				i++
		return false

contains_tagName=(tagName)->
	(a)->
		a.tagName.toUpperCase() is tagName.toUpperCase()

### flat list ###

###
# used only for printing, not needed
flat=(xml, r)->
	if not r
		r = []
	r[r.length] = xml
	i = 0
	if xml.children
		while(i < xml.children.length)
			r = flat(xml.children[i],r)
			i++
	return r

nochild=(arr)->
	i = 0
	while(i < arr.length)
		if(arr[i].children)
			delete arr[i].children
		i++
	arr
###


### TODO: create an object.. ###

fs = require "fs"


#  Here i can get all the stereotypes
getAllStereotypes=(a)->
	queryR select(a).where attribute_name_is_like("base_")

ALL_STEREOTYPES = null

getStereotypeById=(a)->
	query select(ALL_STEREOTYPES).where contains_attribute_equals("xmi:id",a)

getAllClasses=(jPackage)->
	queryR select(jPackage).where contains_attribute_equals("xmi:type","uml:Class")

getAllNamedClasses=(jPackage)->
	isNamedClass=(a)->
		contains_attribute_equals("xmi:type","uml:Class")(a) and is_named()(a)
	queryR select(jPackage).where isNamedClass

#usar meramente para imprimir
getForeignKeys=(jClassList, jClass)->
	isForeignKey=(a)->
		i = 0
		if contains_attribute_equals("xmi:type","uml:Property")(a)
			while(i < jClassList.length)
				return true if a.getAttr("name") is jClassList[i].getAttr("name")
				i++
		return false
	queryR select(jClass).where isForeignKey


getUmlElementById=(jsonData,a)->
	query select(jsonData).where contains_attribute_equals("xmi:id",a)


println=(x)->
	console.log x


special_sort_for_updating_2=(z)->
	#console.log "bublesorting"
	find_index=(name)->
		i = 0
		while(i < z.length)
			if(name is z[i].className)
				return i
			i++
		return -1
	buble = true
	infiniteLoop = 0
	while(buble && infiniteLoop < 100)
		buble = false
		infiniteLoop++;
		hi = 0
		while(hi < z.length)
			i = 0
			while(i < z[hi].fks.length)
				lo = find_index(z[hi].fks[i])
				if(-1 < lo and hi < lo)
					higher = z[lo]
					lower = z[hi]
					z[lo] = lower
					z[hi] = higher
					buble = true
				i++
			hi++
	#println infiniteLoop
	return z

special_sort_for_updating_1=(x)->
	#console.log "quickSorting"
	i = 0
	y = []
	z = []
	while(i < x.length)
		if(x[i].fks.length is 0)
			y[y.length] = x[i]
		else
			z[z.length] = x[i]
		i++
	i = 0
	z = special_sort_for_updating_2(z)
	while(i < z.length)
		y[y.length] = z[i]
		i++
	return y

main_update_class_template=(loops)->
	x = """
	package com.uisleandro.store;

	import android.content.Context;

#{loops[0]}

	import com.uisleandro.store.util.web.TLSUtils;
	import com.uisleandro.store.util.web.TLSWebClient2;

	import java.util.ArrayList;
	import java.util.Iterator;
	import java.util.List;

	/*
	Created by Uisleandro Costa dos Santos
	uisleandro@gmail.com
	
	This class is Responsible to update a Remote Database based on date In the local database
	After comming offline. It's userfull if you're going to collect offline data from a phone
	and then send it to a server.
	*/
	public class OfflineDatabaseUptader {

		TLSUtils utils;

		public OfflineDatabaseUptader(TLSUtils utils) {
			this.utils = utils;
		}

		public void Sync(){

			List<SyncUpdater> syncList = new ArrayList<>();

			Context context = utils.getContext();

			TLSWebClient2 client = new TLSWebClient2(utils);

#{loops[1]}

			Iterator<SyncUpdater> it;

			it = syncList.iterator();
			while(it.hasNext()){
				SyncUpdater that = it.next();
				that.insert_on_client();
			}

			it = syncList.iterator();
			while(it.hasNext()){
				SyncUpdater that = it.next();
				that.update_on_client();
			}

			it = syncList.iterator();
			while(it.hasNext()){
				SyncUpdater that = it.next();
				that.fix_foreign_keys_on_client();
			}

			it = syncList.iterator();
			while(it.hasNext()){
				SyncUpdater that = it.next();
				that.insert_on_server(); //foreign keys translated
			}
			it = syncList.iterator();
			while(it.hasNext()){
				SyncUpdater that = it.next();
				that.update_on_server(); //foreign keys translated
			}
		}

	}
	"""

String.prototype.toCamelCase =()->
	words = this.split('_')
	i = 0
	res = ""
	while i < words.length
		res += words[i].substr(0,1).toUpperCase()+words[i].substr(1)
		i++
	return res

package_class_declaration=(sorted, genPackageName)->
	#import com.uisleandro.store.[].sync.[]
	decl = ""
	i = 0
	while(i < sorted.length)
		decl += "import #{genPackageName}.#{sorted[i].packageName}.sync.#{sorted[i].className.toCamelCase()}Sync;\n"
		i++
	return decl

call_class_sync_code=(sorted, functionName)->
	i = 0
	#syncList.add(new ProductSync(client, context)); //ok
			#[loop] ... list of foreign keys
	decl = ""
	while i < sorted.length
		decl += "\t\t"+functionName+"(new "+sorted[i].className.toCamelCase()+"Sync(client, context));"
		j = 0
		while j < sorted[i].fks.length
			decl += "\n\t\t// #{sorted[i].className} points to: #{sorted[i].fks[j]}"
			j++
		i++
		decl += "\n"

	return decl


first_code_generation_for_updating=(x)->
	declarations = package_class_declaration x,"com.uisleandro.store"
	synccode = call_class_sync_code x,"syncList.add"
	result = main_update_class_template [declarations, synccode]
	return result



# begin adding new code: diagram specific
getPackageByName=(jPackage, name)->
	is_mvc = (a)->
		contains_attribute_equals("xmi:type","uml:Package")(a) and contains_attribute_equals("name",name)(a)
	return queryR select(jPackage).where is_mvc
	# R stands for recursive


# end adding new code: diagram specific


# @main
fs.readFile uml_model,(err,data)->
	if err?
		console.log "error reading"
	else
		jsonData = (new XmlParser()).parse data.toString()
		#ALL_STEREOTYPES = getAllStereotypes(jsonData)

		root_model = jsonData.children[0]
		mvc_package = getPackageByName(root_model,'mvc')[0];
		data_models = getPackageByName(mvc_package,'dataModels')

		# filter all classes
		i = 0
		allClasses = []
		while i < data_models.length
			named_classes = getAllNamedClasses data_models[i]
			j = 0
			while j < named_classes.length
				allClasses[allClasses.length] = named_classes[j]
				j++
			i++

		# funciona mas nao funciona
		#xxx = getStereotypeById("_n2sWIFFIEeesvOUB-zZRiA")
		#console.log JSON.stringify xxx,null,'\t'

		#                root  >  android:pc  >  client*  >  model >  brazilian:cl* >  basic_client:pp *
		#root0 = jsonData.children[0].children[10].children[0].children[0].children[1].children[2]
		#root0 = jsonData.children[0].children[10].children[0].children[0].children[1].children[3] #.getAttr("name")


		#android_package = jsonData.children[0].children[10] #[i].getAttr("name") #children[0].children[j].getAttr("name")
		# android_package = jsonData.children[0].children[10]
		# sqlite_package = jsonData.children[0].children[9]


		#work on it
		# allClasses = queryR select(jsonData).where contains_attribute_equals("xmi:type","uml:Class")
		# allClasses = getAllNamedClasses sqlite_package

		to_sort = [];

		i = 0
		while(i < allClasses.length)
			mClass = allClasses[i]
			#console.log JSON.stringify mClass
			#packageName = mClass.getParent().getAttr("name")
			#className = mClass.getAttr("name")
			that =
				packageName: mClass.getParent().getParent().getAttr("name")
				className: mClass.getAttr("name")
				fks: []
			to_sort[to_sort.length] = that
			#println className
			fks = getForeignKeys allClasses, mClass
			j = 0
			while(j < fks.length)
				that.fks[that.fks.length] = fks[j].getAttr("name")
				#fkName = fks[j].getAttr("name")
				#println "\t\t"+fkName
				j++
			#console.log JSON.stringify
			i++

		SORTED = special_sort_for_updating_1 to_sort

		targetFileContent = first_code_generation_for_updating SORTED

		fs.writeFile target_file, targetFileContent,
		(err)->
			if(err)
				console.log err

		# println JSON.stringify to_sort,null,'  '

		# deopois disso é hora de ordenar os elementos.
		# logo depois poderá ser feita uma geracao de codigo a partir disso

		###
		i = 0
		console.log "\n"
		while i < mPackage.children.length
			mModule = mPackage.children[i]
			moduleName = mModule.getAttr("name")

			if "undefined" isnt typeof moduleName
				console.log moduleName
				j = 0
				while j < mModule.children[0].children.length
					mClass = mModule.children[0].children[j]
					className = mClass.getAttr("name")
					if "undefined" isnt typeof className
						println "\t"+className
						k = 0
						while k < mClass.children.length
							mProperty = mClass.children[k]
							k++
							#  console.log JSON.stringify mProperty,null,'  '
							mPropertyName = mProperty.getAttr("name")
							if "undefined" isnt typeof mPropertyName
								println "\t\t"+mPropertyName
								#console.log mProperty.children[0]
					#console.log mPackage.getAttr("name"), mModule.getAttr("name"), mClass.getAttr("name")
					j++
			i++
		###
		println "\n"
		return

		#console.log JSON.stringify root0,null,'\t'
		#console.log root0

		#console.log root0

		### finding the childrens
		i = 0
		while i < root0.children.length
			console.log i,root0.children[i].getAttr("name"), root0.children[i].getAttr("xmi:type")
			#console.log JSON.stringify root0.children[i].properties
			if root0.children[i].getAttr("xmi:type") is 'uml:Property'
				console.log root0.children[i]
			#console.log i,root0.children[i].getAttr("xmi:id")
			i++
		###

		###
		i = 0
		while i < stereotypes.length
			console.log stereotypes[i].tagName
			i++
		###

		#flatData = nochild(flat(jsonData))
		#flatData = flat jsonData
		#choosen = query select(flatData).where attribute_name_is_like("base_")
		#console.log	JSON.stringify choosen


		#console.log JSON.stringify jsonData,null,'\t'
		#console.log JSON.stringify nochild(flat(xmlData)),null,'\t'

		#console.log	JSON.stringify query(select(flat(xmlData)).where hasTag 'prod'),null,'\t'

		#
		### console.log JSON.stringify obj ###
		###res = query select(obj.children).where(hasTag('vProd'))
		console.log res ###
		### console.log JSON.stringify res ###

		### console.log JSON.stringify (new XmlParser()).parse data.toString() ###


		###
		base_Class
		base_Operation
		base_Property
		base_State




		###

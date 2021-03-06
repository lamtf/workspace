{ special_sort_for_updating_1, println } = require './Util.coffee'
XmlParser  = require './XmlParser.coffee'
XmiQuery  = require './XmiQuery.coffee'
XmlJsPresenter = require './XmlJsPresenter.coffee'
{ config } = require './config.coffee'

fs  = require 'fs'
xmiQuery = new XmiQuery()

uml_model = config.in
target_file = config.out

i = 0
while i < process.argv.length
  if process.argv[i] is "-x"
    uml_model = process.argv[i+1]
    i++
  else if process.argv[i].indexOf("-x") is 0
    uml_model = process.argv[i].substr(2)
  else if process.argv[i] is "-o"
    target_file = process.argv[i+1]
    i++
  else if process.argv[i].indexOf("-o") is 0
    target_file = process.argv[i].substr(2)
  i++
console.log uml_model
console.log target_file


main_update_class_template=(loops)->
  x = """
  package #{config.package};

  import android.content.Context;

#{loops[0]}

  import #{config.package}.util.web.TLSUtils;
  import #{config.package}.util.web.TLSWebClient2;

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


package_class_declaration=(sorted, genPackageName)->
  #import com.uisleandro.store.[].sync.[]
  decl = ""
  i = 0
  while(i < sorted.length)
    decl += "import #{genPackageName}.#{sorted[i].packageName}.sync.#{sorted[i].className.toCamelCase()}OfflineDataSync;\n"
    i++
  return decl


call_class_sync_code=(sorted, functionName)->
  i = 0
  decl = ""
  while i < sorted.length
    decl += "\t\t"+functionName+"(new "+sorted[i].className.toCamelCase()+"OfflineDataSync(client, context));"
    j = 0
    while j < sorted[i].fks.length
      decl += "\n\t\t// #{sorted[i].className} points to: #{sorted[i].fks[j]}"
      j++
    i++
    decl += "\n"
  return decl

first_code_generation_for_updating=(x)->
  declarations = package_class_declaration x,config.package
  synccode = call_class_sync_code x,"syncList.add"
  result = main_update_class_template [declarations, synccode]
  return result

# @main
fs.readFile uml_model,(err,data)->
  if err?
    console.log "error reading"
  else
    jsonData = (new XmlParser()).parse data.toString()
    console.log "jsonData"
    #ALL_STEREOTYPES = getAllStereotypes(jsonData)

    root_model = jsonData.children[0]
    mvc_package = xmiQuery.getPackageByName(root_model,'mvc')[0];
    data_models = xmiQuery.getPackageByName(mvc_package,'dataModels')

    # filter all classes
    i = 0
    allClasses = []
    while i < data_models.length
      named_classes = xmiQuery.getAllNamedClasses data_models[i]
      j = 0
      while j < named_classes.length
        allClasses[allClasses.length] = named_classes[j]
        j++
      i++

    console.log "allClasses"
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
    # allClasses = queryR select(jsonData).where contains_attribute_equals("xmiQuery:type","uml:Class")
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
      fks = xmiQuery.getForeignKeys allClasses, mClass
      j = 0
      while(j < fks.length)
        that.fks[that.fks.length] = fks[j].getAttr("name")
        #fkName = fks[j].getAttr("name")
        #println "\t\t"+fkName
        j++
      #console.log JSON.stringify
      i++

    console.log "foreign-keys"

    SORTED = special_sort_for_updating_1 to_sort

    console.log "sorted"

    targetFileContent = first_code_generation_for_updating SORTED

    console.log "template-ready"

    fs.writeFile target_file, targetFileContent,
    (err)->
      if(err)
        console.log err
      console.log "finished"

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
    return

    #console.log JSON.stringify root0,null,'\t'
    #console.log root0

    #console.log root0

    ### finding the childrens
    i = 0
    while i < root0.children.length
      console.log i,root0.children[i].getAttr("name"), root0.children[i].getAttr("xmiQuery:type")
      #console.log JSON.stringify root0.children[i].properties
      if root0.children[i].getAttr("xmiQuery:type") is 'uml:Property'
        console.log root0.children[i]
      #console.log i,root0.children[i].getAttr("xmiQuery:id")
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
    #console.log  JSON.stringify choosen


    #console.log JSON.stringify jsonData,null,'\t'
    #console.log JSON.stringify nochild(flat(xmlData)),null,'\t'

    #console.log  JSON.stringify query(select(flat(xmlData)).where hasTag 'prod'),null,'\t'

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

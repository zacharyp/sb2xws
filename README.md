# sb2xws

Translates X-wing squadbuilder id to XWS JSON

## 

Usage, take the UUID (unique identifier) of your squadbuilder squad, i.e the `8d86dd8d-1ff8-4e07-a9cb-37582fe0655f` out of the URL `https://squadbuilder.fantasyflightgames.com/squad-preview/8d86dd8d-1ff8-4e07-a9cb-37582fe0655f`


Then go to http://sb2xws.herokuapp.com/translate/8d86dd8d-1ff8-4e07-a9cb-37582fe0655f
 (editing for your squd's UUID), and you will be presented with XWS JSON, for example:
 
```
{"faction":"scumandvillainy","pilots":[{"id":"l337-escapecraft","ship":"escapecraft","upgrades":{"crew":["tacticalofficer"]},"points":24},{"id":"bobafett","ship":"firesprayclasspatrolcraft","upgrades":{"crew":["perceptivecopilot"],"gunner":["hansolo-gunner"],"title":["marauder"],"ept":["debrisgambit"]},"points":99},{"id":"fennrau","ship":"fangfighter","upgrades":{"ept":["fearless"]},"points":71}],"name":"Boba Fenn L3-37","description":"","points":194}
```

This JSON can be imported into either [YASB](https://raithos.github.io) or [Vassal X-wing module](http://www.vassalengine.org/wiki/Module:Star_Wars:_X-Wing_Miniatures_Game).

### xwing-data2 submodule
`git submodule foreach git pull origin master`

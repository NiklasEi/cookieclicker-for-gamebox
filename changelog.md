### v 2.2.1
- add command to manipulate cookies of player saves

### v 2.2.0
- compatibility with minecraft 1.13

### v 2.1.3
- fix players being able to take out items after closing the game in the right moment. In most cases it were just ghost items.
- remove leftover debug messages from last update

### v 2.1.2
- fix upgrades not being displayed
- fix upgrade ordering
- fix cutoff when more then 8 upgrades should be displayed in the bottom row
- fix top list updates

### v 2.1.1
- fix NPE due to game save being loaded at a time where the inventory isn't initialized jet

### v 2.1.0
- sync top lists via plugin channels
- improved cookie clicker database
  - save games in MySQL database when enabled
  - all handled in database class instead of Game and Manager classes

### v 2.0.0
- update to gamebox v2

#

### v 1.1.1
* update lang_zh-cn
* compatibility for minecraft 1.8

### v 1.1.0
* merged #5
  * add lang_zh-cn to default language files
* added buildings up to prism
* upgrades for new buildings

### v 1.0.0
* full language support for all buildings and upgrades
* improved default style of the lores and titles
* use number formatting with fixed cutoff

### v 0.2.0
* completed Cursor, Grandma, Mine, Factory, clicking and Bank upgrades
* corrected titanium mouse cost
* fix missing CPC update after new calculation of CPS
* fix possible NPE with new language code (for later i18n)

### v 0.1.3
* move and improve number formatting
* add number formatting to top list (through GameBox!)
* no more score cut-off in top list
* GB dep is up to 1.5.5
* fix titanium mouse upgrade (had wrong requirements and cost)

### v 0.1.2
* farm updates are completed
* cookie will move after configured amount of clicks
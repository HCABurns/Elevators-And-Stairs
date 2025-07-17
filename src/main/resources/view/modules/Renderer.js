import { api as entityModule } from '../entity-module/GraphicEntityModule.js';

export class Renderer {
  static get moduleName() {
    return 'Renderer';
  }


  handleFrameData(frameInfo, frameData) {



  }


  updateScene(previousData, currentData, progress) {



  }

  reinitScene(){

  }

  animateScene (delta) {

  }

  handleGlobalData (players, globalData) {
     this.globalData = {
       players: players,
       playerCount: players.length
     }
  }

}
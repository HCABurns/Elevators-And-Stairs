import { api as entityModule } from '../entity-module/GraphicEntityModule.js';

export class Renderer {
  static get moduleName() {
    return 'Renderer';
  }


  handleFrameData(frameInfo, frameData) {
    return {frameInfo,frameData,};
  }


  updateScene(previousData, currentData, progress) {
      console.log(previousData)
      console.log(currentData)
      console.log(progress)

    if (currentData && currentData.frameData && currentData.frameData.brodo) {
      console.log("Brodo Updated")

      const brodoData = currentData.frameData.brodo;
      console.log(brodoData)

      const nextX = brodoData.x * 256;
      //const nextY = brodoData.y * 256;
      const currentX = brodoData.prevX * 256;
      //const currentY = brodoData.prevY * 256;

      const nextY = (3 - 1 - brodoData.y) * 256;
      const currentY = (3 - 1 - brodoData.prevY) * 256;


      const diffX = (nextX - currentX);
      const diffY = (nextY - currentY);

      const entity = entityModule.entities.get(brodoData.id);
      console.log(entity);
      if (entity) {

        if (diffX != 0 || diffY != 0){
        console.log("Run Here 2")
            entity.graphics.x = currentX + (diffX * progress);
            entity.graphics.y = currentY + (diffY * progress);
        }else{
        console.log("Run Here 1")
            entity.graphics.x = currentX * progress
            entity.graphics.y = currentY * progress
        }
      }
    }
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
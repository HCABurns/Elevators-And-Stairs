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

      const nextX = (brodoData.x) * 256;
      const currentX = (brodoData.prevX) * 256;

      const nextY = (3 - 1 - brodoData.y) * 256;
      const currentY = (3 - 1 - brodoData.prevY) * 256;

      const diffX = (nextX - currentX);
      const diffY = (nextY - currentY);

      const entity = entityModule.entities.get(brodoData.id);
      console.log(entity);
      if (entity) {
        if (diffX != 0 || diffY != 0){
            entity.graphics.x = currentX + (diffX * progress);
            entity.graphics.y = currentY + (diffY * progress);
            if (diffX < 0) {
              entity.graphics.scale.x = -brodoData.scaleX;
              entity.graphics.anchor.x = 1;
            } else if (diffX > 0) {
              entity.graphics.scale.x = brodoData.scaleX;
              entity.graphics.anchor.x = 0;
            }
        }else{
            entity.graphics.x = currentX;
            entity.graphics.y = currentY;
            entity.graphics.anchor.x = 0;
        }
      }
    }
    else{
        console.log("Brodo not updated")
        console.log(previousData)
        if (previousData && previousData.frameData &&previousData.frameData.brodo){
            const brodoData = previousData.frameData.brodo
            const entity = entityModule.entities.get(brodoData.id);
            if (entity){
                entity.graphics.anchor.x = 0;
                entity.graphics.scale.x = brodoData.scaleX;
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
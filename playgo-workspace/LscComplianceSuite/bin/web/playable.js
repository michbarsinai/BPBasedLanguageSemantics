
var serverStarted = false;

//DWR 
function startServer()
{
	NJAppServer.start(startServerCallback);	
}

//startServerCallback
function startServerCallback(result)
{
	if(result==true){
		serverStarted = true;				
	}
}
		
//activateMehods (called from animation loop)
function activateMehods()
{
	NJAppServer.getMethodToActivate(getMethodToActivateCallBack);
}

//getMethodToActivateCallBack
function getMethodToActivateCallBack(methodToActivate)
{
	if(methodToActivate==null){
		return;
	}
	activateMethod(methodToActivate);
}


//right click
document.addEventListener('contextmenu', onRightClick, false);

//onRightClick
function onRightClick(event) 
{
	intersectionInit(event);
	if(visibleIntersects==null){
		return;
	}
//	alert('Intersected object: ' + visibleIntersects.objectName + 
//		' X: ' + event.clientX + 'Y: ' + event.clientY);
	
	NJAppServer.playgoObjectRightClicked(
			visibleIntersects.className,
			visibleIntersects.objectName,
			event.clientX, event.clientY+85);
}
		
var INTERSECTED;

var _mouse = new THREE.Vector3();
var _projector = new THREE.Projector();

//intersectionInit
function intersectionInit(event)
{
	if(renderer==null || renderer.domElement==null){
		return false;
	}
	event.preventDefault(); 

	_mouse.x = ( (event.clientX) / renderer.domElement.width) * 2 - 1; 
	_mouse.y = -( (event.clientY) / renderer.domElement.height) * 2 + 1;
	
	ray = _projector.pickingRay(_mouse, camera);
	intersects = ray.intersectObjects( scene.children );

	visibleIntersects=null;
	var objName;
	
	if ( intersects!=undefined && intersects.length > 0)
	{
		for(var i=0;i<intersects.length;i++){
			if(intersects[i].object.visible==true){
				objName = intersects[i].object.objectName;
				if(objName!='floor' && objName!='skyBox'){
					visibleIntersects=intersects[i].object;
					return true;
				}
			}
		}
	}
	return true;
}
		

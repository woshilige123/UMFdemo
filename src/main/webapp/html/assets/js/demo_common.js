/**
 * 
 */

function cart_item(name, price, quantity, img_url, model){
	this.name = name;
	this.price = price;
	this.quantity = quantity;
	this.img_url = img_url;
	this.model = model;
	this.getTotalPrice = function(){
		return price*quantity;
	};
}


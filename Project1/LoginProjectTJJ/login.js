var submitBtn = document.getElementById("submitBtn");

submitBtn.addEventListener("mouseout", function() {
    submitBtn.removeAttribute("disabled"); // enables the button
    submitBtn.classList.remove("btn-danger") // removes the color red
    submitBtn.classList.add("btn-warning"); // adds the color yellow
});

submitBtn.addEventListener("mouseenter", function(event) {   
    submitBtn.classList.add("btn-danger") // sets the color to red
    submitBtn.setAttribute("disabled", true); // disables the button xD
});

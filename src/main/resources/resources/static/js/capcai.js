function acak_captcha() {
  // di FE pake ini
  // var no_acak = Math.floor(Math.random() * 1000000 + 1);
  // kalo BE setup dimari om
  var no_acak = "om paul";
  var posisi_x = Math.floor(Math.random() * 50 + 1);
  var posisi_y = Math.floor(Math.random() * 50 + 10);
  var c = document.getElementById("myCanvas");
  var canvas = c.getContext("2d");
  canvas.clearRect(0, 0, 150, 60);
  var gradient = canvas.createLinearGradient(0, 0, c.width, 0);
  gradient.addColorStop("0", "blue");
  gradient.addColorStop("1", "yellow");
  canvas.fillStyle = gradient;
  canvas.beginPath();
  canvas.rect(0, 0, 150, 60);
  canvas.fill();

  var ctx = c.getContext("2d");
  ctx.fillStyle = "#000000";
  ctx.font = "18px serif";
  ctx.strokeText(no_acak, posisi_x, posisi_y);
  document.getElementById("captcha").setAttribute("c", no_acak);
}
window.onload = function () {
  acak_captcha();
};

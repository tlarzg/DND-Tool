exports.config =
  # See http://brunch.io/#documentation for docs.
  paths:
    public: '../webapp'
  npm:
    enabled: true
  files:
    javascripts:
      joinTo:
        'vendor.js': /^(app\/vendor)|(node_modules)/
        'main.js': /^app\/(?!vendor)/
    stylesheets:
      joinTo: 'app.css'
    templates:
      joinTo: 'main.js'
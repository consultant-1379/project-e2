const externals = {
  apps: [{
    path: "app-1",
    entry: "App1"
  }],
  components: {
    default: [],
    shareable: [{
      path: "start-component",
      entry: "StartComponent"
    }, {
      path: "questionaire-component",
      entry: "QuestionaireComponent"
    }, {
      path: "question-component",
      entry: "QuestionComponent"
    }, {
      path: "graph-component",
      entry: "GraphComponent"
    }, {
      path: "results-component",
      entry: "ResultsComponent"
    }]
  },
  panels: [],
  plugins: []
};
module.exports = externals;
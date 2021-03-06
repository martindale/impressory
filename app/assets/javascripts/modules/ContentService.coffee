define(["./app"], () ->

  Impressory.angularApp.service('ContentService', ['$http', '$location', '$cacheFactory', 'viewingContent', ($http, $location, $cacheFactory, viewingContent) ->
   
    cache = $cacheFactory('contentServiceCache')
   
    {

      # Returns a promise containing the JSON of a content entry
      get: (courseId, entryId) ->
        key = { course: courseId, entry: entryId }
        cache.get(key) || (
          promise =  $http.get("/course/#{courseId}/entry/#{entryId}").then((res) -> res.data)
          cache.put(key, promise)
          promise
        )
    
      embedUrl: (courseId, entryId) -> 
        if $location.port() == 80
          "#{$location.protocol()}://#{$location.host()}/course/#{courseId}/embedContent?entryId=#{entryId}"
        else
          "#{$location.protocol()}://#{$location.host()}:#{$location.port()}/course/#{courseId}/embedContent?entryId=#{entryId}"
    
      voteDown: (entry) ->
        $http.post("/course/#{entry.course}/entry/#{entry.id}/voteDown").then((res) ->          
          angular.copy(res.data, entry)
        )

      voteUp: (entry) ->
        $http.post("/course/#{entry.course}/entry/#{entry.id}/voteUp").then((res) ->
          angular.copy(res.data, entry)
        )
        
      addComment: (entry, text) ->  
        $http.post("/course/#{entry.course}/entry/#{entry.id}/addComment", { text: text }).then((res) ->
          angular.copy(res.data, entry)
        )
        
      allEntries: (courseId) -> $http.get("/course/#{courseId}/allEntries").then((res) -> res.data.entries)
        
      activity: (courseId) -> $http.get("/course/#{courseId}/activity").then((res) -> res.data)
      
      whatIsIt: (code) -> $http.get("/whatIsIt", { params: { code : code } })
        
      viewPath: (entry) -> "/course/#{entry.course}/viewContent?entryId=#{entry.id}"
      
      addContent: (courseId, entry) -> $http.post("/course/#{courseId}/addContent", entry )

      # Asks the server to update a content entry's item      
      editItem: (entry) -> $http.post("/course/#{entry.course}/entry/#{entry.id}/editItem", entry).then(
        (res) ->
          entry = res.data 
          viewingContent.updateEntryInPlace(entry)
          entry 
        (res) -> res.data
      )
      
      # Asks the server to update a content entry's tags, settings, and metadata      
      editTags: (entry) -> $http.post("/course/#{entry.course}/entry/#{entry.id}/editTags", entry).then(
        (res) -> 
          entry = res.data 
          viewingContent.updateEntryInPlace(entry)
          entry 
        (res) -> res.data
      )
        
      # Identifies the viewer component to include, depending on the type of content.
      # The returned string is the path to the Angular.js partial template.
      viewerPartialUrl: (kind) -> 
        switch kind
          when 'Multiple choice poll' then '/partials/viewcontent/kinds/multipleChoicePoll.html'
          when 'Markdown page' then '/partials/viewcontent/kinds/markdownPage.html'
          when 'Google Slides' then '/partials/viewcontent/kinds/googleSlides.html'
          when 'sequence' then '/partials/viewcontent/kinds/contentSequence.html'
          when 'web page' then '/partials/viewcontent/kinds/webPage.html'
          when 'YouTube video' then '/partials/viewcontent/kinds/youTubeVideo.html'
          else 'partials/viewcontent/kinds/noContent.html'

      # Identifies the viewer component to include, depending on the type of content.
      # The returned string is the path to the Angular.js partial template.
      streamPartialUrl: (kind) -> 
        switch kind
          when 'YouTube video' then '/partials/viewcontent/stream/youTubeVideo.html'
          when 'Markdown page' then '/partials/viewcontent/stream/markdownPage.html'
          when 'Multiple choice poll' then '/partials/viewcontent/stream/multipleChoicePoll.html'
          else '/partials/viewcontent/stream/default.html'
          
          
      # Identifies the editor component to include, depending on the type of content.
      # The returned string is the path to the Angular.js partial template.
      editPartialUrl: (kind) -> 
        switch kind
          when 'sequence' then '/partials/editcontent/kinds/contentSequence.html'
          when 'Google Slides' then '/partials/editcontent/kinds/googleSlides.html'
          when 'Markdown page' then '/partials/editcontent/kinds/markdownPage.html'
          when 'Multiple choice poll' then '/partials/editcontent/kinds/multipleChoicePoll.html'
          when 'web page' then '/partials/editcontent/kinds/webPage.html'
          when 'YouTube video' then '/partials/editcontent/kinds/youTubeVideo.html'
          else 'partials/editcontent/kinds/default.html'          
    }
  ])


)
           <style>
           	
				#external-events {

					padding: 0 10px;
					border: 1px solid #ccc;
					background: #eee;
					text-align: left;
				}
					
				#external-events h4 {
					font-size: 16px;
					margin-top: 0;
					padding-top: 1em;
				}
					
				#external-events .fc-event {
					margin: 10px 0;
					cursor: pointer;
				}
					
				#external-events p {
					margin: 1.5em 0;
					font-size: 11px;
					color: #666;
				}
					
				#external-events p input {
					margin: 0;
					vertical-align: middle;
				}
			
           </style>
           
            <div  class="box boxextend" >
				<div class="box-body ">
                  <!-- THE CALENDAR -->
                  
                <div class="row">
                <div class="col-md-2">
	                  <div id='external-events'>
							<h4>请拖动任务</h4>
							<div class='fc-event'>总行会议</div>
							<div class='fc-event'>培训</div>
							<div class='fc-event'>授权业务学习</div>
							<div class='fc-event'>大客户会见</div>
							<div class='fc-event'>IT业务培训</div>
							<p>
								<input type='checkbox' id='drop-remove' />
								<label for='drop-remove'>移动后删除</label>
							</p>
					 </div>
				</div>		  
                <div class="col-md-10">  
                  <div id="${model.objUid}"></div>
                </div>
                </div>  <!-- /.row -->
                </div><!-- /.box-body -->
            </div><!-- /. box -->
              <script>
              
              
					$('#external-events .fc-event').each(function() {
			
						// store data so the calendar knows to render an event upon drop
						$(this).data('event', {
							title: $.trim($(this).text()), // use the element's text as the event title
							stick: true // maintain when user navigates (see docs on the renderEvent method)
						});
			
						// make the event draggable using jQuery UI
						$(this).draggable({
							zIndex: 999,
							revert: true,      // will cause the event to go back to its
							revertDuration: 0  //  original position after the drag
						});
			
					});

                    $('#${model.objUid}').fullCalendar({
                         lang: 'zh-cn',
                         editable: true,
						 droppable: true, // this allows things to be dropped onto the calendar
						 drop: function() {
							// is the "remove after drop" checkbox checked?
							if ($('#drop-remove').is(':checked')) {
								// if so, remove the element from the "Draggable Events" list
								$(this).remove();
							}
						}
			                         
                    });

              </script>
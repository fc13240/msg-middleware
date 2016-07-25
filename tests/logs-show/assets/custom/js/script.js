jQuery(document).ready(function () {
    'use strict';


    /* Flickity */

    jQuery('#featuresSlider').flickity({
        cellAlign: 'left',
        contain: true,
        prevNextButtons: false
    });

    jQuery('#showcaseSlider').flickity({
        cellAlign: 'left',
        contain: true,
        prevNextButtons: false,
        imagesLoaded: true
    });

    /* Preloader */

    setTimeout(function(){
        jQuery('body').addClass('loaded');
        //jQuery('h1').css('color','#222222');
    }, 1000);

    jQuery(window).load(function () {

        /* Sticky Header */

        jQuery(".sticky-header").sticky({topSpacing: 0});

        /* FlexSlider */

        jQuery('.flexslider').flexslider({
            animation: "slide",
            controlNav: false,
            prevText: "",
            nextText: ""
        });

    });

    /* Isotope Portfolio */

    var $container = jQuery('.isotope-container').imagesLoaded( function() {
      $container.isotope({
        // options
      });
    });

    /* activate jquery isotope */
    var $container = jQuery('#posts').isotope({
      itemSelector : '.item',
      isFitWidth: true
    });

    jQuery(window).smartresize(function(){
      $container.isotope({
        columnWidth: '.col-md-3'
      });
    });

    $container.isotope({ filter: '*' });

    // filter items on button click
    jQuery('#isotope-filters').on( 'click', 'a', function() {
      var filterValue = jQuery(this).attr('data-filter');
      $container.isotope({ filter: filterValue });
    });

  /* Superfish dropdown menu */

    jQuery('ul.sf-menu').superfish({
        delay: 600,                            // one second delay on mouseout
        speed: 'fast',                          // faster animation speed
        animation: {opacity: 'show', height: 'show'},
        disableHI:  true,
        autoArrows: false                            // disable generation of arrow mark-up
    });

    jQuery('#mobnav-btn').click(
        function () {
            jQuery('.sf-menu').toggleClass("xactive");
        }
    );

    jQuery('.mobnav-subarrow').click(
        function () {
            jQuery(this).parent().toggleClass("xpopdrop");
        }
    );

    /* Bootstrap Carousel */

    jQuery('#myCarousel').carousel({
        interval: 8000
    });

    // handles the carousel thumbnails
    jQuery('[id^=carousel-selector-]').click(function () {
        var id_selector = jQuery(this).attr("id");
        var id = id_selector.substr(id_selector.length - 1);
        id = parseInt(id);
        jQuery('#myCarousel').carousel(id);
        jQuery('[id^=carousel-selector-]').removeClass('selected');
        jQuery(this).addClass('selected');
    });

    // when the carousel slides, auto update
    jQuery('#myCarousel').on('slid', function (e) {
        var id = jQuery('.item.active').data('slide-number');
        id = parseInt(id);
        jQuery('[id^=carousel-selector-]').removeClass('selected');
        jQuery('[id=carousel-selector-' + id + ']').addClass('selected');
    });

    /* Stellar Parallax */

    jQuery.stellar({
        //responsive: true,
        //horizontalOffset: 0,
        //verticalOffset: 0,
        horizontalScrolling: false,
        //parallaxBackgrounds: true,
        //verticalOffset: 0,
        //responsive: true
    });

    // Hero image
    // var document_height = jQuery( window ).height();
    // var document_width = jQuery( window ).width();
    // jQuery(".heroimage-wrap").height(document_height);
    //
    // var header_height = jQuery(".outer-wrap").outerHeight() * -1;
    // console.log(header_height);
    // if (header_height!==0) {
    //     // $("#heroimage1").css("marginTop",header_height);
    // }
    // jQuery(window).resize(function() {
    //
    //     document_height = jQuery( window ).height();
    //
    //     if ( jQuery(".outer-wrap").is(":visible") ) {
    //         // header_height = $(".'.esc_js($offsetclass).'").outerHeight() * -1;
    //         // $("#heroimage1").css({"marginTop":header_height,"background-size":"cover"});
    //     } else {
    //         jQuery("#heroimage").css({"marginTop":"0","background-size":"cover"});
    //     }
    //     jQuery(".heroimage-wrap").height(document_height);
    // });

    /* Slider Revolution */

    if (jQuery('.tp-banner').length) {
        jQuery('.tp-banner').show().revolution(
            {
                dottedOverlay: "none",
                delay: 9000,
                startwidth: 1170,
                startheight: 900,
                hideThumbs: 200,

                thumbWidth: 100,
                thumbHeight: 50,
                thumbAmount: 5,

                navigationType: "none",
                navigationArrows: "solo",
                navigationStyle: "preview1",

                touchenabled: "off",
                onHoverStop: "on",

                parallax: "scroll",
                parallaxBgFreeze: "on",
                parallaxLevels: [10, 7, 4, 3, 2, 5, 4, 3, 2, 1],
                parallaxDisableOnMobile: "off",

                swipe_velocity: 0.7,
                swipe_min_touches: 1,
                swipe_max_touches: 1,
                drag_block_vertical: false,


                keyboardNavigation: "on",

                navigationHAlign: "center",
                navigationVAlign: "bottom",
                navigationHOffset: 0,
                navigationVOffset: 20,

                soloArrowLeftHalign: "left",
                soloArrowLeftValign: "center",
                soloArrowLeftHOffset: 20,
                soloArrowLeftVOffset: 0,

                soloArrowRightHalign: "right",
                soloArrowRightValign: "center",
                soloArrowRightHOffset: 20,
                soloArrowRightVOffset: 0,

                shadow: 0,
                fullWidth: "on",
                fullScreen: "on",

                spinner: "spinner0",

                stopLoop: "off",
                stopAfterLoops: -1,
                stopAtSlide: -1,

                shuffle: "off",

                autoHeight: "off",
                forceFullWidth: "off",


                hideThumbsOnMobile: "off",
                hideNavDelayOnMobile: 1500,
                hideBulletsOnMobile: "off",
                hideArrowsOnMobile: "off",
                hideThumbsUnderResolution: 0,

                hideSliderAtLimit: 0,
                hideCaptionAtLimit: 0,
                hideAllCaptionAtLilmit: 0,
                startWithSlide: 0
            });
    }

    /* Back to top */

    jQuery("#back-top").hide();

    jQuery(window).scroll(function () {
         if ($(this).scrollTop() > 100) {
             $('#back-top').fadeIn();
         } else {
             $('#back-top').fadeOut();
         }
    });

    jQuery('#back-top a').click(function () {
         $('body,html').animate({
             scrollTop: 0
         }, 600);
         return false;
    });

    /* Flickr feed */

    // jQuery('#basicuse').jflickrfeed({
    //     limit: 10,
    //     qstrings: {
    //         id: '32532032@N06'
    //     },
    //     itemTemplate: '<li>' +
    //     '<a href="{{image_b}}"><img src="{{image_s}}" class="img-rounded" alt="{{title}}" /></a>' +
    //     '</li>'
    // });

    /* Google Analytics */

    /* Google map */

    // add by xq
    jQuery('.dot').eq(0).click(function(){
      $('#features-bg').fadeIn('slow');
      $('#features-bg1').hide();
      $('#features-bg2').hide();
    });
    jQuery('.dot').eq(1).click(function(){
      $('#features-bg').hide();
      $('#features-bg1').fadeIn('slow');
      $('#features-bg2').hide();
    });
    jQuery('.dot').eq(2).click(function(){
      $('#features-bg').hide();
      $('#features-bg1').hide();
      $('#features-bg2').fadeIn('slow');
    });
});

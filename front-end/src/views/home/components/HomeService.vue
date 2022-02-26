<template>
  <div>
	<br><br><br><br><br><br><br>
	<div class="d-flex justify-content-start py-5">
		<div class="col-1"></div>
		<div class="col-5">
			<h1 style="text-align: left; font-size: 60px;">
				늘비상담과 함께하는 <br><br>
				주요 서비스에 대해 알아볼까요?
			</h1>
		</div>
	</div>
    <div class="d-flex justify-content-center py-5">
      <div class="card col-5">
        <figure class="card__thumb" style="background-color: #c1d1f6;">
          <img src="https://m.catholictimes.org/uploadFile2/article_template/273853_3345_1.JPG"
			alt="Picture by Daniel Lincoln"
			class="card__image"
			style="opacity: 0.5; width: 95%;"
			>
          <figcaption class="card__caption">
            <h2 class="card__title">고해성사</h2>
            <p class="card__snippet">“누구나 부담없이 서로의 이야기를 말없이 공감해주는 서비스”<br><br>
							아무 말도 필요없다. 그저 공감할 뿐!!<br><br>
							마음으로 소통해요~💌
						</p>
          </figcaption>
        </figure>
      </div>
			<div class="col-1"></div>
      <div class="card col-5">
				<figure class="card__thumb" style="background-color: #c1d1f6;">
          <img src="https://mblogthumb-phinf.pstatic.net/MjAxOTA1MjNfMTc2/MDAxNTU4NTY0ODc5MDc3.TYl9532or-ZAG67ZMi884hjUV6geU_eQCLjbyZRSmDcg.cryBdjxe62Gh6RhrAwDm6YsDV9Sv61cmCapEkH2nOo8g.JPEG.mckko/%EC%83%81%EB%8B%B4_%EC%9D%BC%EB%9F%AC%EC%8A%A4%ED%8A%B8_%EB%A7%8C%ED%99%94.jpg?type=w800"
			alt="Picture by Daniel Lincoln"
			class="card__image"
			style="opacity: 0.5; width: 95%; height: 567.88px;"
			>
          <figcaption class="card__caption">
			
            <h2 class="card__title">고민상담</h2>
            <p class="card__snippet">“나의 고민은 너의 고민, 너의 고민은 나의 고민”<br><br>
							쉽게 말하지 못했던 나의 마음을 속 시원히 말하자!!
						</p>
			
          </figcaption>
        </figure>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "HomeService",
}
</script>

<style lang="scss" scoped>
$desktop: 1024px;

@mixin breakpoint($point) {
  @if $point == desktop {
    @media (min-width: $desktop) {
      @content;
		}
	}
}

html {
  box-sizing: border-box;
  font-size: 62.5%;

  *,
  *:before,
  *:after {
    box-sizing: inherit;
  }
}

body {
	display: flex;
	justify-content: center;
	align-items: center;
	width: 100vw;
	height: 100vh;
	background-color: #e6e6e6;
}

.container {
	display: flex;
	flex-wrap: wrap;
	justify-content: center;
	width: 100%;
	margin: 20px 0 80px;
}

.card {
	width: 600px;
	margin: 10px;
	background-color: white;
	box-shadow: 0 5px 10px 0 rgba(0, 0, 0, 0.5);
	border-radius: 30px;

	&:hover {
		.card__caption {
			display: flex;
			flex-direction: column;
			justify-content: center;
			top: 50%;
			transform: translateY(-50%);
		}

		.card__image {
			transform: translateY(-10px);
			opacity: 0.2;
		}

		.card__thumb {
			&::after {
				top: 0;
			}
		}

		.card__snippet {
			margin: 60px 0;
		}
	}

	&__thumb {
		position: relative;
		max-height: 500px;
		overflow: hidden;
		border-radius: 30px;
		
		@include breakpoint(desktop) {
			max-height: 500px;
		}

		&::after {
			position: absolute;
			top: 0;
			display: block;
			content: '';
			width: 100%;
			height: 100%;
			background: linear-gradient(0deg, rgba(240, 234, 234, 0.5) 40%, rgba(255, 255, 255, 0) 100%);
			transition: .3s;
			
			@include breakpoint(desktop) {
				top: calc(100% - 100px);
			}
		}
	}
	
	&__image {
		transition: .5s ease-in-out;
		border-radius: 30px;
		margin-top: 10pt;
	}
	
	&__caption {
		position: absolute;
		top: 50%;
		width: 100%;
		z-index: 1;
		padding: 0 20px;
		color: black;
		transform: translateY(-50%);
		text-align: center;
		transition: .3s;

		@include breakpoint(desktop) {
			top: calc(100% - 80px);
			transform: unset;
		}
	}

	&__title {
		display: -webkit-box;
		max-height: 85px;
		overflow: hidden;
		font-size: 50px;
		line-height: 50px;
		text-shadow: 0px 1px 2px black;
		text-overflow: ellipsis;
		-webkit-line-clamp: 3;
		-webkit-box-orient: vertical;
	}

	&__snippet {
		display: -webkit-box;
		max-height: 150px;
		margin: 20px 0;
		overflow: hidden;
		font-size: 25px;
		font-weight: bold;
		line-height: 30px;
		text-overflow: ellipsis;
		transition: .5s ease-in-out;
		-webkit-line-clamp: 5;
		-webkit-box-orient: vertical;
		
		@include breakpoint(desktop) {
			margin: 60px 0;
		}
	}

	&__button {
		display: inline-block;
		padding: 10px 20px;
		color: white;
		border: 1px solid white;
		font-family: 'Roboto', sans-serif;
		font-size: 12px;
		text-transform: uppercase;
		text-decoration: none;
		transition: .3s;

		&:hover {
			color: black;
			background-color: white;
		}
	}
}

// .disclaimer {
// 	position: fixed;
// 	bottom: 0;
// 	left: 50%;
// 	z-index: 2;
// 	box-sizing: border-box;
// 	width: 100%;
// 	padding: 20px 10px;
// 	background-color: white;
// 	transform: translateX(-50%);
// 	font-family: 'Roboto', sans-serif;
// 	font-size: 14px;
// 	text-align: center;
	
// 	&__link {
// 		color: #755D87;
// 		text-decoration: none;
// 	}
// }

</style>